package com.example.hotelmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@IdClass(BookingRoomsAssociation.BookingRoomsAssociationId.class)
public class BookingRoomsAssociation {

    @ManyToOne(cascade = CascadeType.ALL)
    @Id
    @JoinColumn(name="booking_id", referencedColumnName = "booking_id", nullable = false)
    private Booking booking;

    @JsonBackReference(value = "booking-rel")
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @Id
    @JoinColumn(name="room_id", referencedColumnName = "room_id", nullable = false)
    private Room room;

    @JsonBackReference(value = "room-rel")
    public void setRoom(Room room) {
        this.room = room;
    }

    public static class BookingRoomsAssociationId implements Serializable {
        private Long booking;
        private Long room;

    }
}