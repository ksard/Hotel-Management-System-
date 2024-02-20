package com.example.hotelmanagementsystem.controllers;

import com.example.hotelmanagementsystem.dto.AuthResponseDto;
import com.example.hotelmanagementsystem.dto.ResponseDto;
import com.example.hotelmanagementsystem.dto.UserCredentialDto;
import com.example.hotelmanagementsystem.models.Person;
import com.example.hotelmanagementsystem.models.SharedData;
import com.example.hotelmanagementsystem.services.AuthService;
import com.example.hotelmanagementsystem.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final GuestService guestService;
    private final AuthService authService;
    private final LoggingController loggingController;

    @Autowired
    public AuthController(GuestService guestService, AuthService authService, LoggingController loggingController)
    {
        this.guestService = guestService;
        this.authService=authService;
        this.loggingController = loggingController;
    }
    @PostMapping("/login")
    public ResponseEntity Login(@RequestBody UserCredentialDto user)
    {
        try{

            String token = authService.Login(user);
            if(token!=null)
            {
                AuthResponseDto authResponseDto=new AuthResponseDto(){{accessToken=token;}};
                return new ResponseEntity(authResponseDto, HttpStatusCode.valueOf(200));//{{status=SharedData._Success; statusCode= SharedData._SuccessCode;}};
            }
        }
        catch (Exception e)
        {
            loggingController.logger.error(e.getMessage());
        }
        return new ResponseEntity(HttpStatusCode.valueOf(401));
    }

    @PostMapping("/register")
    public ResponseDto<AuthResponseDto> register(@RequestBody Person person) {
        ResponseDto responseDto=new ResponseDto(){{status= SharedData._Failed;statusCode=SharedData._FailedCode;}};
        try{
            UserCredentialDto user=new UserCredentialDto();
            {
                {
                    user.password =person.getPassword();
                }
                {
                    user.email=person.getEmail();
                }
            }
            Long personId=guestService.registerGuest(person);
            if(personId==-1)
            {
                responseDto.statusMessage=SharedData._EmailExists;
                loggingController.logger.error("Email already exists");
            }
            if(personId==0)
            {
                responseDto.statusMessage=SharedData._RegistrationFailed;
                loggingController.logger.error("Registration failed");
            }
            if(personId>0)
            {

                String token=authService.Login(user);
                if(token==null)
                {
                    responseDto.statusMessage=SharedData._RegSuccessfulLoginFailed;
                    responseDto.status=SharedData._Success;
                    responseDto.statusCode=SharedData._PartialCode;
                    loggingController.logger.warn("Login failed. Registration succeeded");
                    return responseDto;
                }
                responseDto.statusMessage=SharedData._RegistrationSuccessful;
                responseDto.status=SharedData._Success;
                responseDto.statusCode=SharedData._SuccessCode;
                responseDto.content=new AuthResponseDto(){{accessToken=token;}};
            }
        }
        catch (Exception e)
        {
            responseDto.statusMessage=e.getMessage();
            loggingController.logger.error(e.getMessage());
        }
        return responseDto;
    }


}
