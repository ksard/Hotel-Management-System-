package com.example.hotelmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="AdditionalGuests")
@Getter
@Setter
@NoArgsConstructor
public class AdditionalGuest extends BasicInformation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long additionalGuestId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="booking_id", referencedColumnName = "booking_id")
    private Booking booking;
    @JsonBackReference(value = "booking-add")
    public Booking getBooking() {
        return  this.booking;
    }
}
