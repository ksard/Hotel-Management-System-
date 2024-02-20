package com.example.hotelmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BasicInformation extends CommonInfo {
    @Column(name="first_name",nullable = false,length=50)
    private String firstName;
    @Column(name="last_name",nullable = false,length=50)
    private String lastName;
    @Column(name="dob",nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date DOB;
    @Column(name="gender",nullable = false,length=20)
    private String gender;
    @Column(name="identity_proof",length=100)
    private String identityProof;
    @Column(name="identity_proof_type",length=50)
    private String identityProofType;

}
