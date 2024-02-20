package com.example.hotelmanagementsystem.services;

import com.example.hotelmanagementsystem.models.*;

import java.util.ArrayList;
import java.util.List;

public interface RoomService {

    List<Service> getAllServices();

    List<RoomType> getAllRoomTypes();

    List<Object[]> getRoomsByType(String checkInDate, String checkOutDate);
    List<Holiday> fetchHolidays();
    ArrayList<Holiday> createHolidays(ArrayList<Holiday> holidays);


    }
