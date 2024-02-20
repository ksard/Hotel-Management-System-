package com.example.hotelmanagementsystem.services.impl;

import com.example.hotelmanagementsystem.controllers.LoggingController;
import com.example.hotelmanagementsystem.models.AdditionalGuest;
import com.example.hotelmanagementsystem.models.Booking;
import com.example.hotelmanagementsystem.repositories.AdditionalGuestRepository;
import com.example.hotelmanagementsystem.services.AdditionalGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AdditionalGuestServiceImpl implements AdditionalGuestService {


    private final AdditionalGuestRepository additionalGuestRepository;
    private final LoggingController loggingController;
    public AdditionalGuestServiceImpl(AdditionalGuestRepository additionalGuestRepository, LoggingController loggingController){
        this.additionalGuestRepository = additionalGuestRepository;
        this.loggingController = loggingController;
    }

    @Override
    public List<AdditionalGuest> insertGuests(Booking booking) {
        try{
            List<AdditionalGuest> additionalGuestsCopy = new ArrayList<>(booking.additionalGuests);

            additionalGuestsCopy.forEach(g -> {
                g.setCreatedBy(null);
                g.setModifiedBy(null);
                g.setBooking(booking);
            });

            return additionalGuestRepository.saveAll(additionalGuestsCopy);
        }catch (Exception e) {
            loggingController.logger.error(e.getMessage());        }
        return null;
    }
}
