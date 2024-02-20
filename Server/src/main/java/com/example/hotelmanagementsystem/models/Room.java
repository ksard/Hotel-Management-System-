package com.example.hotelmanagementsystem.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="Rooms")
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "roomId")
public class Room extends CommonInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="room_type_id", referencedColumnName = "room_type_id")
    private RoomType roomType;
    private int floor;



}
