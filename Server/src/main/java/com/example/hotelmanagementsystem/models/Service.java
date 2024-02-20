package com.example.hotelmanagementsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Services")
@Getter
@Setter
@NoArgsConstructor
public class Service extends CommonInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;
    private String serviceName;
    private Double price;
    private String serviceIcon;
}
