package com.example.hotelmanagementsystem.dto;

import com.example.hotelmanagementsystem.models.Booking;

import java.util.Optional;


public class BookingResponseDto extends  ResponseDto{
    public Optional<Booking> content;
}
