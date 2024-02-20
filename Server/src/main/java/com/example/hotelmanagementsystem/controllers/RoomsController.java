package com.example.hotelmanagementsystem.controllers;

import com.example.hotelmanagementsystem.dto.ResponseDto;
import com.example.hotelmanagementsystem.dto.RoomsDto;
import com.example.hotelmanagementsystem.dto.RoomsResponseDto;
import com.example.hotelmanagementsystem.models.RoomType;
import com.example.hotelmanagementsystem.models.Service;
import com.example.hotelmanagementsystem.models.SharedData;
import com.example.hotelmanagementsystem.services.RoomService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/rooms")
public class RoomsController {

    private final RoomService roomService;
    private final LoggingController loggingController;

    @Autowired
    public RoomsController(RoomService roomService, LoggingController loggingController)
    {
        this.roomService=roomService;
        this.loggingController = loggingController;
    }
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    public RoomsResponseDto GetRooms(@RequestBody RoomsDto roomsDto)
    {
        RoomsResponseDto roomsResponseDto=new RoomsResponseDto(){ { status= SharedData._Success; statusCode=SharedData._SuccessCode; } };
        try{
            List<Object[]> roomsByTypeQueryResult = roomService.getRoomsByType(roomsDto.checkInDate, roomsDto.checkOutDate);

            HashMap<Pair<String, String>, Long> roomsByTypeResult = new HashMap<>();
            for(Object[] o : roomsByTypeQueryResult) {
                roomsByTypeResult.put(new Pair<>((String) o[0], (String) o[1]), (long) o[2]);
            }
            roomsResponseDto.roomsByTypeContent = roomsByTypeResult;
            roomsResponseDto.roomTypesContent = roomService.
                    getAllRoomTypes();
            roomsResponseDto.servicesContent = roomService.getAllServices();
            roomsResponseDto.statusMessage = SharedData._RoomTypesServicesFetched;
            loggingController.logger.info("Successfully fetched all available rooms between " + roomsDto.checkInDate + " and " + roomsDto.checkOutDate + ".");
        }
        catch(Exception e)
        {
            roomsResponseDto.status=SharedData._Failed;
            roomsResponseDto.statusCode=SharedData._FailedCode;
            roomsResponseDto.statusMessage=e.getMessage();
            loggingController.logger.error(e.getMessage());
        }
        return roomsResponseDto;
    }
    @GetMapping("/services")
    public ResponseDto<List<Service>> GetAllServices()
    {
        ResponseDto<List<Service>> servicesResponseDto= new ResponseDto<>() {
            {
                status = SharedData._Success;
                statusCode = SharedData._SuccessCode;
            }
        };
        try{
            servicesResponseDto.content = roomService.getAllServices();
           servicesResponseDto.statusMessage=SharedData._SuccessFetchServicesMsg;
           loggingController.logger.info(SharedData._SuccessFetchServicesMsg);
        } catch (Exception e) {
            servicesResponseDto.status = SharedData._Failed;
            servicesResponseDto.statusCode = SharedData._FailedCode;
            servicesResponseDto.statusMessage = e.getMessage();
            loggingController.logger.error(e.getMessage());
        }
        return servicesResponseDto;
    }

    @GetMapping("/types")
    public ResponseDto<List<RoomType>> GetAllRoomTypes()
    {
        ResponseDto<List<RoomType>> servicesResponseDto= new ResponseDto<>() {
            {
                status = SharedData._Success;
                statusCode = SharedData._SuccessCode;
            }
        };
        try{
            servicesResponseDto.content = roomService.getAllRoomTypes();
            servicesResponseDto.statusMessage=SharedData._SuccesRoomTypeFetch;
            loggingController.logger.info(SharedData._SuccesRoomTypeFetch);
        } catch (Exception e) {
            servicesResponseDto.status = SharedData._Failed;
            servicesResponseDto.statusCode = SharedData._FailedCode;
            servicesResponseDto.statusMessage = e.getMessage();
            loggingController.logger.error(e.getMessage());
        }
        return servicesResponseDto;
    }
}
