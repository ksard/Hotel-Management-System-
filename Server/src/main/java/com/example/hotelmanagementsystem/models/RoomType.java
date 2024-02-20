package com.example.hotelmanagementsystem.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.example.hotelmanagementsystem.models.Service;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name="RoomTypes")
@Getter
@Setter
@NoArgsConstructor
public class RoomType extends CommonInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_id")
    private Long roomTypeId;
    private String type;
    private String view;
    private double basePrice;
    private String includedServices;
    @JsonIgnore
    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Room> room;
    @JsonBackReference(value = "room-roomtype")
    public List<Room> getRoomType() {
        return this.room;
    }
    private Long surgeFeeBeforeAWeek;
    private Long surgeFeeAfterAWeek;
    private Long surgeFeeOngoingWeek;
    private Long surgeFeeCurrentDay;
    private Long weekendSurgeFee;
    @Column(name = "description")
    private String description;
    @Column(name = "image", length = 25)
    private String image;

}

