package com.example.hotelmanagementsystem.services;

import com.example.hotelmanagementsystem.dto.UserCredentialDto;

public interface AuthService {
    String Login(UserCredentialDto userCredentialDto);

}
