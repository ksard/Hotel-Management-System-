package com.example.hotelmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="Holidays")
@Getter
@Setter
@NoArgsConstructor
public class Holiday extends CommonInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holidayId;

    private String holidayName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="room_type_id", referencedColumnName = "room_type_id")
    private RoomType roomType;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date toDate;
    private int surgeFeePercentage;

}
