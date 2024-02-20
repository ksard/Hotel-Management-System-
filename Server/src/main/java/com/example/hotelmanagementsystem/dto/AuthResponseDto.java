package com.example.hotelmanagementsystem.dto;


import com.example.hotelmanagementsystem.models.SharedData;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthResponseDto {
    public String accessToken;
    public String tokenType;

    public  AuthResponseDto()
    {
        this.tokenType= SharedData._TokenType;
    }

}