package com.example.hotelmanagementsystem.services.impl;

import com.example.hotelmanagementsystem.controllers.LoggingController;
import com.example.hotelmanagementsystem.dto.UserCredentialDto;
import com.example.hotelmanagementsystem.security.JwtGenerator;
import com.example.hotelmanagementsystem.services.AuthService;
import com.example.hotelmanagementsystem.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final GuestService guestService;
    private final JwtGenerator jwtGenerator;
    private final LoggingController loggingController;

    public AuthServiceImpl(AuthenticationManager authenticationManager, GuestService guestService, JwtGenerator jwtGenerator, LoggingController loggingController)
    {
        this.authenticationManager=authenticationManager;
        this.guestService=guestService;
        this.jwtGenerator=jwtGenerator;
        this.loggingController = loggingController;
    }

    @Override
    public String Login(UserCredentialDto user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.email,
                            user.password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Long personId = guestService.findGuestId(user.email);
            if (personId > 0) {
                return jwtGenerator.generateToken(authentication, personId);
            }
        }
        catch (Exception e)
        {
            loggingController.logger.error(e.getMessage());
        }
        return null;
    }
}
