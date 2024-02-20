package com.example.hotelmanagementsystem.services.impl;

import com.example.hotelmanagementsystem.controllers.LoggingController;
import com.example.hotelmanagementsystem.models.Holiday;
import com.example.hotelmanagementsystem.models.RoomType;
import com.example.hotelmanagementsystem.repositories.HolidayRepository;
import com.example.hotelmanagementsystem.repositories.RoomRepository;
import com.example.hotelmanagementsystem.repositories.RoomTypeRepository;
import com.example.hotelmanagementsystem.repositories.ServiceRepository;
import com.example.hotelmanagementsystem.services.RoomService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    RoomTypeRepository roomTypeRepository;

    @Autowired
    LoggingController loggingController;

    @Autowired
    HolidayRepository holidayRepository;


    @Override
    public List<com.example.hotelmanagementsystem.models.Service> getAllServices() {
        try {
            return serviceRepository.findAll();
        } catch (Exception e) {
            loggingController.logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<RoomType> getAllRoomTypes() {
        try {
            return roomTypeRepository.findAll();
        } catch (Exception e) {
            loggingController.logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Object[]> getRoomsByType(String checkInDate, String checkOutDate) {
        try {
            return roomRepository.findFreeRoomsByType(checkInDate, checkOutDate);
        } catch (Exception e) {
            loggingController.logger.error(e.getMessage());
        }
        return null;
    }


    @Override
    public ArrayList<Holiday> createHolidays(ArrayList<Holiday> holidays) {
        try {
            holidays.forEach(h->{
                h.setCreatedBy(null);
                h.setModifiedBy(null);
                h.setRoomType(entityManager.find(RoomType.class, h.getRoomType().getRoomTypeId()));
            });
            return (ArrayList<Holiday>) holidayRepository.saveAll(holidays);
        } catch (Exception e) {
            loggingController.logger.error(e.getMessage());
        }
        return null;
    }

    public List<Holiday> fetchHolidays(){
        try {
            return holidayRepository.findAll();
        } catch (Exception e) {
            loggingController.logger.error(e.getMessage());
        }
        return null;
    }

}
