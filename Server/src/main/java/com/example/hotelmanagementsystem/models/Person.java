package com.example.hotelmanagementsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="People")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Person extends BasicInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long personId;
    @Column(name="email",nullable = false,length=100, unique = true)
    private String email;
    @Column(name="phone_no",nullable = false,length=20)
    private String phoneNo;
    @Column(name="address",nullable = false,length=300)
    private String address;
    @Column(name="password",length=200)
    private String password;
    @Column(name="deleted",nullable = false)
    private Boolean deleted = false;
    @Column(name="type",nullable = false,length=10)
    private String type;


}
