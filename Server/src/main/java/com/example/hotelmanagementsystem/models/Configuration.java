package com.example.hotelmanagementsystem.models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Configuration {
    @Value("${jwt.expiry-time}")
    private Long expiryTime;

}
