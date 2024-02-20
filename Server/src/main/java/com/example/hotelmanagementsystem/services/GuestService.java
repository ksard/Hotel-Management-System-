package com.example.hotelmanagementsystem.services;

import com.example.hotelmanagementsystem.models.Guest;
import com.example.hotelmanagementsystem.models.Person;

public interface GuestService {
    Long registerGuest(Person person);
    Long findGuestId(String email);

}
