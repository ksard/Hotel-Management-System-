package com.example.hotelmanagementsystem.services;

import com.example.hotelmanagementsystem.models.AdditionalGuest;
import com.example.hotelmanagementsystem.models.Booking;

import java.util.List;

public interface AdditionalGuestService {
    List<AdditionalGuest> insertGuests(Booking booking);
}
