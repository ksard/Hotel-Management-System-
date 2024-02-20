package com.example.hotelmanagementsystem.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="Employees")
@Getter
@Setter
@NoArgsConstructor
//@ApiModel(description = "This table holds cloud vendor information.")
public class Employee extends Person{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    @ApiModelProperty(notes="This is a Cloud Vendor Id. It shall be unique.")
//    public Long employeeId;

//    @OneToOne
//    @JoinColumn(name = "personId")
//    private Person person;
@Column(name="role",nullable = false,length=25)
private String role;
    @Column(name="joining_date",nullable = false)
    private Date joiningDate;
    @Column(name="employment_status",nullable = false, length =25)
    private String employmentStatus;

}
