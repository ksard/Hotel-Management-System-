package com.example.hotelmanagementsystem.controllers;

import com.example.hotelmanagementsystem.dto.*;
import com.example.hotelmanagementsystem.models.Booking;
import com.example.hotelmanagementsystem.models.SharedData;
import com.example.hotelmanagementsystem.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/room")
public class BookingsController {
    private final BookingService bookingService;
    private final LoggingController loggingController;
    @Autowired
    public BookingsController(BookingService bookingService, LoggingController loggingController){
        this.bookingService = bookingService;
        this.loggingController = loggingController;
    }


    @PostMapping("/book")
    public BookingResponseDto bookRoom(@RequestBody MakeBookingDto booking){
        BookingResponseDto bookingResponseDto=new BookingResponseDto(){ { status= SharedData._Success; statusCode=SharedData._SuccessCode; } };
        try{
            Optional<Booking> newBooking = bookingService.createBooking(booking);

            if(newBooking.isPresent()){
                bookingResponseDto.content=newBooking;
                bookingResponseDto.statusMessage=SharedData._SuccessMeg;
                loggingController.logger.info("Room booked successful. BookingId: "+newBooking.get().getBookingId());
            }
            else {
                bookingResponseDto.status = SharedData._Failed;
                bookingResponseDto.statusCode=SharedData._FailedCode;
                bookingResponseDto.statusMessage=SharedData._FailedMeg;
                loggingController.logger.error("Failed to create new booking!");
            }

        }
        catch(Exception e)
        {
            bookingResponseDto.status=SharedData._Failed;
            bookingResponseDto.statusCode=SharedData._FailedCode;
            bookingResponseDto.statusMessage=e.getMessage();
            loggingController.logger.error(e.getMessage());
        }
        return bookingResponseDto;
    }
    @PostMapping("/cancel/{bookingId}")
    public IdResponseDto cancelRoom(@PathVariable Long bookingId){
        IdResponseDto IdResponseDto=new IdResponseDto(){ { status= SharedData._Success; statusCode=SharedData._SuccessCode; } };
        try{
            int status = bookingService.updateBookingStatus(bookingId,SharedData._Cancellation);

            if(status >= 1){
                IdResponseDto.content=bookingId;
                IdResponseDto.statusMessage=SharedData._SuccessBookingCancelMeg;
                loggingController.logger.info("Room sucessfully canceled with booking ID " + bookingId);
            }
            else {
                IdResponseDto.content = null;
                IdResponseDto.status = SharedData._Failed;
                IdResponseDto.statusCode=SharedData._FailedCode;
                IdResponseDto.statusMessage=SharedData._FailedBookingCancelMeg;
                loggingController.logger.error("Booking status of booking ID " + bookingId + " cannot be canceled");
            }

        }
        catch(Exception e)
        {
            IdResponseDto.status=SharedData._Failed;
            IdResponseDto.statusCode=SharedData._FailedCode;
            IdResponseDto.statusMessage=e.getMessage();
            loggingController.logger.error(e.getMessage());
        }
        return IdResponseDto;
    }
    @PostMapping("/checkin/{bookingId}")
    public IdResponseDto checkInRoom(@PathVariable Long bookingId){
        IdResponseDto IdResponseDto=new IdResponseDto(){ { status= SharedData._Success; statusCode=SharedData._SuccessCode; } };
        try{
            int status = bookingService.updateBookingStatus(bookingId,SharedData._CheckIn);

            if(status >= 1){
                IdResponseDto.content=bookingId;
                IdResponseDto.statusMessage=SharedData._SuccessCheckInMsg;
                loggingController.logger.info("Sucessfully checked in guest at booking ID " + bookingId);
            }
            else {
                IdResponseDto.content = null;
                IdResponseDto.status = SharedData._Failed;
                IdResponseDto.statusCode=SharedData._FailedCode;
                IdResponseDto.statusMessage=SharedData._FailedCheckInMsg;
                loggingController.logger.error("Cannot check in guest at booking ID " + bookingId);
            }
        }
        catch(Exception e)
        {
            IdResponseDto.status=SharedData._Failed;
            IdResponseDto.statusCode=SharedData._FailedCode;
            IdResponseDto.statusMessage=e.getMessage();
            loggingController.logger.error(e.getMessage());
        }
        return IdResponseDto;
    }
    @PostMapping("/checkout/{bookingId}")
    public IdResponseDto checkOutRoom(@PathVariable Long bookingId){
        IdResponseDto IdResponseDto=new IdResponseDto(){ { status= SharedData._Success; statusCode=SharedData._SuccessCode; } };
        try{
            int status = bookingService.updateBookingStatus(bookingId,SharedData._CheckOut);

            if(status >= 1){
                IdResponseDto.content=bookingId;
                IdResponseDto.statusMessage=SharedData._SuccessCheckOutMsg;
                loggingController.logger.info("Successfully checked out guest at booking ID " + bookingId);
            }
            else {
                IdResponseDto.content = null;
                IdResponseDto.status = SharedData._Failed;
                IdResponseDto.statusCode=SharedData._FailedCode;
                IdResponseDto.statusMessage=SharedData._FailedCheckOutMsg;
                loggingController.logger.error("Failed to check out guest at booking ID " + bookingId);
            }
        }
        catch(Exception e)
        {
            IdResponseDto.status=SharedData._Failed;
            IdResponseDto.statusCode=SharedData._FailedCode;
            IdResponseDto.statusMessage=e.getMessage();
            loggingController.logger.error(e.getMessage());
        }
        return IdResponseDto;
    }

    @PostMapping("/refund/{bookingId}")
    public IdResponseDto refund(@PathVariable Long bookingId){
        IdResponseDto IdResponseDto=new IdResponseDto(){ { status= SharedData._Success; statusCode=SharedData._SuccessCode; } };
        try{
            int status = bookingService.updateBookingStatus(bookingId,SharedData._Refund);

            if(status >= 1){
                IdResponseDto.content=bookingId;
                IdResponseDto.statusMessage=SharedData._SuccessRefundMsg;
                loggingController.logger.info("Successfully refunded booking with ID " + bookingId);
            }
            else {
                IdResponseDto.content = null;
                IdResponseDto.status = SharedData._Failed;
                IdResponseDto.statusCode=SharedData._FailedCode;
                IdResponseDto.statusMessage=SharedData._FailedRefundMsg;
                loggingController.logger.error("Failed to refund booking with ID " + bookingId);
            }

        }
        catch(Exception e)
        {
            IdResponseDto.status=SharedData._Failed;
            IdResponseDto.statusCode=SharedData._FailedCode;
            IdResponseDto.statusMessage=e.getMessage();
            loggingController.logger.error(e.getMessage());
        }
        return IdResponseDto;
    }
    @GetMapping("/bookings")
    public ResponseDto<List<FetchedBookingData>> getBookings(){
        ResponseDto<List<FetchedBookingData>> bookingResponseDto=new ResponseDto<>(){ { status= SharedData._Success; statusCode=SharedData._SuccessCode; } };
        try{
            bookingResponseDto.content = bookingService.getAllBookings();
            if (bookingResponseDto.content != null){
                bookingResponseDto.statusMessage = SharedData._SuccessFetchBookingMsg;
                loggingController.logger.info(SharedData._SuccessFetchBookingMsg);
            }
            else{
                bookingResponseDto.status = SharedData._Failed;
                bookingResponseDto.statusCode=SharedData._FailedCode;
                bookingResponseDto.statusMessage=SharedData._FailedMeg;
                loggingController.logger.error("Failed to fetch bookings");
            }
        }
        catch(Exception e)
        {
            bookingResponseDto.status=SharedData._Failed;
            bookingResponseDto.statusCode=SharedData._FailedCode;
            bookingResponseDto.statusMessage=e.getMessage();
            loggingController.logger.error(e.getMessage());
        }
        return bookingResponseDto;
    }
    @PostMapping("/services")
    public ResponseDto<UpdateOptedServices> AddServiceToBooking(@RequestBody UpdateOptedServices services)
    {
        ResponseDto<UpdateOptedServices> responseDto=new ResponseDto<UpdateOptedServices>(){{status= SharedData._Success; statusCode=SharedData._SuccessCode;}};

        try{
            int updated=bookingService.updateOptedServices(services);
            if(updated>0) {
                responseDto.content=services;
                responseDto.statusMessage=SharedData._ServicesAdded;
                loggingController.logger.info(SharedData._ServicesAdded);
            }
            else{
                responseDto.status=SharedData._Failed;
                responseDto.statusCode=SharedData._FailedCode;
                responseDto.statusMessage=SharedData._ServicesAdditionFailed;
                loggingController.logger.info(SharedData._ServicesAdditionFailed);
            }
        }
        catch (Exception e)
        {
            responseDto.status=SharedData._Failed;
            responseDto.statusCode=SharedData._FailedCode;
            responseDto.statusMessage=e.getMessage();
            loggingController.logger.error(e.getMessage());
        }
        return responseDto;
    }
}