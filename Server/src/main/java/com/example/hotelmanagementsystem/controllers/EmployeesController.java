package com.example.hotelmanagementsystem.controllers;

import com.example.hotelmanagementsystem.dto.ResponseDto;
import com.example.hotelmanagementsystem.models.Holiday;
import com.example.hotelmanagementsystem.models.SharedData;
import com.example.hotelmanagementsystem.services.GuestService;
import com.example.hotelmanagementsystem.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeesController {
    private final LoggingController loggingController;
    private final RoomService roomService;


    @Autowired
    public EmployeesController(GuestService guestService, LoggingController loggingController,  RoomService roomService) {
        this.loggingController = loggingController;
        this.roomService = roomService;
    }

    @PostMapping("/holidays")
    public ResponseDto<ArrayList<Holiday>> CreateHolidays(@RequestBody ArrayList<Holiday> holidays)
    {
        ResponseDto<ArrayList<Holiday>> responseDto=new ResponseDto<>(){ { status= SharedData._Success; statusCode=SharedData._SuccessCode; } };
        try{
            holidays=roomService.createHolidays(holidays);
            if(holidays!=null) {
                responseDto.content=holidays;
                responseDto.statusMessage=SharedData._CreatedHolidays;
                loggingController.logger.info(SharedData._CreatedHolidays);
            }
            else{
                responseDto.status=SharedData._Failed;
                responseDto.statusCode=SharedData._FailedCode;
                responseDto.statusMessage=SharedData._HolidaysCreationFailed;
                loggingController.logger.info(SharedData._HolidaysCreationFailed);

            }
        }
        catch (Exception e) {
            responseDto.status=SharedData._Failed;
            responseDto.statusCode=SharedData._FailedCode;
            responseDto.statusMessage=e.getMessage();
            loggingController.logger.error(e.getMessage());
        }
        return responseDto;
    }

    @GetMapping("/holidays")
    public ResponseDto<List<Holiday>> FetchHolidays()
    {
        ResponseDto<List<Holiday>> responseDto=new ResponseDto<>(){ { status= SharedData._Success; statusCode=SharedData._SuccessCode; } };
        try{
            List<Holiday> holidays = roomService.fetchHolidays();
            if(holidays!=null) {
                responseDto.content=holidays;
                responseDto.statusMessage=SharedData._FetchedHolidays;
                loggingController.logger.info(SharedData._FetchedHolidays);
            }
            else{
                responseDto.status=SharedData._Failed;
                responseDto.statusCode=SharedData._FailedCode;
                responseDto.statusMessage=SharedData._FailedFetchedHolidays;
                loggingController.logger.info(SharedData._FailedFetchedHolidays);

            }
        }
        catch (Exception e) {
            responseDto.status=SharedData._Failed;
            responseDto.statusCode=SharedData._FailedCode;
            responseDto.statusMessage=e.getMessage();
            loggingController.logger.error(e.getMessage());
        }
        return responseDto;
    }

}
