package com.example.hotelmanagementsystem.models;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@IdClass(RoomsAndRoomType.RoomsAndRoomTypeId.class)
public class RoomsAndRoomType {
    @Id
    public Long roomTypeId;
    public long getRoomTypeId()
    {
        return this.roomTypeId;
    }
    @Id
    public  Long roomId;

    public static class RoomsAndRoomTypeId implements Serializable {
        private Long roomTypeId;  // Corrected field name to match the entity field name
        private Long roomId;     // Corrected field name to match the entity field name

        // Getters, setters, and constructors
    }
}
