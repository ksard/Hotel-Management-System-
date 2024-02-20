package com.example.hotelmanagementsystem.services.impl;


import com.example.hotelmanagementsystem.controllers.LoggingController;

import com.example.hotelmanagementsystem.dto.FetchedBookingData;
import com.example.hotelmanagementsystem.dto.MakeBookingDto;
import com.example.hotelmanagementsystem.dto.UpdateOptedServices;
import com.example.hotelmanagementsystem.models.*;
import com.example.hotelmanagementsystem.repositories.*;
import com.example.hotelmanagementsystem.services.AdditionalGuestService;
import com.example.hotelmanagementsystem.services.BookingService;
import com.example.hotelmanagementsystem.services.GuestService;
import com.example.hotelmanagementsystem.services.PaymentService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final EntityManager entityManager;

    private final LoggingController loggingController;

    private final PaymentService paymentService;
    private final RoomsAndRoomTypeRepository roomRepository;
    private final AdditionalGuestService additionalGuestService;
    private final BookingRoomsAssociationRepository bookingRoomsAssociationRepository;
    private final GuestService guestService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, EntityManager entityManager, PaymentService paymentService, RoomsAndRoomTypeRepository roomRepository, AdditionalGuestService additionalGuestService, BookingRoomsAssociationRepository bookingRoomsAssociationRepository, GuestService guestService, LoggingController loggingController) {
        this.bookingRepository = bookingRepository;
        this.entityManager = entityManager;
        this.paymentService = paymentService;
        this.roomRepository = roomRepository;
        this.additionalGuestService = additionalGuestService;
        this.bookingRoomsAssociationRepository = bookingRoomsAssociationRepository;
        this.guestService = guestService;
        this.loggingController = loggingController;
    }

    @Override
    public Optional<Booking> createBooking(MakeBookingDto bookingRequest) {
        try {
            Long personId = bookingRequest.booking.getGuest().getPersonId();
            if (personId == null) {
                loggingController.logger.info("Primary guest id is null");
                personId = guestService.registerGuest(bookingRequest.booking.getGuest());
                if(personId==-1)
                {
                    personId=guestService.findGuestId(bookingRequest.booking.getGuest().getEmail());
                }
            }
            if (personId == null || personId <= 0) {
                loggingController.logger.info("Primary guest insertion failed");
                bookingRequest.booking.setBookingStatus(SharedData._RefundInitiated);
                return Optional.ofNullable(bookingRequest.booking);
            }
            loggingController.logger.info("Primary guest id: "+personId);
            Guest guest = entityManager.find(Guest.class, personId);
            bookingRequest.booking.setGuest(guest);

            String roomTypes = bookingRequest.roomsSelected.keySet().stream().map(Object::toString).collect(Collectors.joining(","));
            List<RoomsAndRoomType> roomsAvailable = roomRepository.checkRoomAvailability(bookingRequest.booking.getCheckInDate().toString(), bookingRequest.booking.getCheckOutDate().toString(), roomTypes);
            loggingController.logger.info("checkRoomAvailability returned room count: "+roomsAvailable.size());
            Map<Long, List<RoomsAndRoomType>> groupsRoomsAvailable = roomsAvailable.stream().collect(Collectors.groupingBy((RoomsAndRoomType::getRoomTypeId)));
            if (groupsRoomsAvailable.keySet().size() < bookingRequest.roomsSelected.keySet().size()) {
                bookingRequest.booking.setBookingStatus(SharedData._RefundInitiated);
                loggingController.logger.info("Rooms unavailable for one or more types. Refund initiated if payment made. ");
                return Optional.of(bookingRequest.booking);
            }
            List<BookingRoomsAssociation> bookingRoomsAssociations = new ArrayList<>();

            groupsRoomsAvailable.forEach((name, group) -> {
                if (!(bookingRequest.roomsSelected.containsKey(name)
                        && bookingRequest.roomsSelected.get(name) <= group.size())) {
                    loggingController.logger.info("Number of available rooms for room type "+name+" is lower than required number "+group.size());
                    return;
                }

                group.stream().limit(bookingRequest.roomsSelected.get(name)).forEach(r -> {
                            BookingRoomsAssociation association = new BookingRoomsAssociation();
                            association.setRoom(entityManager.find(Room.class, r.roomId));
                            bookingRoomsAssociations.add(association);
                        }
                );

            });
            loggingController.logger.info("Booking and rooms association created. Count: "+bookingRoomsAssociations.size());

            Payment payment = paymentService.insertPayment(bookingRequest.booking.getPayment());
            if (payment == null) {
                loggingController.logger.error("Payment failed");
                return Optional.empty();
            }

            bookingRequest.booking.setPayment(payment);
            bookingRequest.booking.setBookingStatus(SharedData._Confirmation);
            bookingRequest.booking.setGuest(guest);
            bookingRequest.booking.setModifiedBy(null);
            bookingRequest.booking.setCreatedBy(null);

            Booking bookingInserted = bookingRepository.save(bookingRequest.booking);
            if (bookingInserted == null) {
                //payment refund initiation needs to be implemented here
                loggingController.logger.error("Booking insertion failed");

                bookingRequest.booking.setBookingStatus(SharedData._RefundInitiated);
                return Optional.of(bookingRequest.booking);
            }
            List<AdditionalGuest> guests = additionalGuestService.insertGuests(bookingRequest.booking);
            bookingRequest.booking.setAdditionalGuests(guests);
            bookingRoomsAssociations.forEach(b -> b.setBooking(bookingInserted));
            bookingRoomsAssociationRepository.saveAll(bookingRoomsAssociations);
            return Optional.of(bookingInserted);
        } catch (Exception e) {
            bookingRequest.booking.setBookingStatus(SharedData._RefundInitiated);
            loggingController.logger.error(e.getMessage());
            return Optional.ofNullable(bookingRequest.booking);
        }
    }

    @Override
    public List<FetchedBookingData> getAllBookings() {
        return bookingRepository.FetchBookings();
    }

    @Override
    public int updateBookingStatus(Long bookingId, String newStatus) {
        int updated=0;
        try {
            loggingController.logger.info("Status updated to " + newStatus + " for booking " + bookingId + ".");
            updated=bookingRepository.updateBookingStatus(bookingId, newStatus);
            if(updated<=0)
            {
                loggingController.logger.error("Status updated to " + newStatus + " for booking " + bookingId + " failed");
            }
        }
        catch (Exception e)
        {
            loggingController.logger.error(e.getMessage());
        }
        return updated;
    }

    @Override
    public int updateOptedServices(UpdateOptedServices updateOptedServices)
    {
        int updated=0;
        try{
            updated=bookingRepository.updateOptedServices(updateOptedServices.bookingId, updateOptedServices.optedServices);
            if(updated>0)
            {
                loggingController.logger.info("Updating optedServices for bookingid "+updateOptedServices.bookingId+" success");
            }
            else {
                loggingController.logger.error("Updating optedServices for bookingid "+updateOptedServices.bookingId+" failed");
            }
        }
        catch (Exception e)
        {
            loggingController.logger.error(e.getMessage());
        }
        return  updated;
    }



}
