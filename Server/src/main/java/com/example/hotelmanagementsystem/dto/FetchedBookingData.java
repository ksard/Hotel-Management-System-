package com.example.hotelmanagementsystem.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public interface FetchedBookingData {


    Long getBookingId();

    String getOptedServices();

    Float getTotalPrice();

    String getBookingDate();

    @JsonFormat(pattern = "YYYY-MM-dd")
    LocalDate getCheckinDate();

    @JsonFormat(pattern = "YYYY-MM-dd")
    LocalDate getCheckoutDate();

    Long getNumOfGuest();

    String getBookingStatus();
    Long getGuestId();
    String getEmail();
    String getPhoneNo();
    int getRoomCount();
    String getFirstName();
    String getLastName();

}