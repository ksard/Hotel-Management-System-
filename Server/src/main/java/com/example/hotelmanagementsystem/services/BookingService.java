package com.example.hotelmanagementsystem.services;

import com.example.hotelmanagementsystem.dto.FetchedBookingData;
import com.example.hotelmanagementsystem.dto.MakeBookingDto;
import com.example.hotelmanagementsystem.dto.UpdateOptedServices;
import com.example.hotelmanagementsystem.models.Booking;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    Optional<Booking> createBooking(MakeBookingDto bookingRequest);
     List<FetchedBookingData> getAllBookings() ;
    int updateBookingStatus(Long bookingId, String newStatus);
    int updateOptedServices(UpdateOptedServices updateOptedServices);

}
