package com.example.hotelmanagementsystem.models;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table (name="Booking")
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "bookingId")
public class Booking extends CommonInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="guest_id", referencedColumnName = "person_id",nullable = false)
    private Guest guest;

    public void setGuest(Guest guest) {
        this.guest = guest;
    }


    @Column(name="opted_services",length=50)
    private String optedServices;

    @Column(name="room_price",nullable=false)
    private float roomPrice;

    @Column(name="total_price",nullable=false)
    private float totalPrice;

    @CreationTimestamp
    @Column(name="booking_date")
    private String bookingDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="checkin_date",nullable=false)
    @Temporal(TemporalType.DATE)
    private LocalDate checkInDate;


    @Column(name="checkout_date",nullable=false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate checkOutDate;

    @Column(name="num_of_guest")
    private Long numOfGuest;

    @Column(name="booking_status",nullable = false,length = 20)
    private String bookingStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="payment_id", referencedColumnName="payment_id",nullable = false)
    public Payment payment;
    @JsonBackReference(value = "booking-payment-set")
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    @JsonBackReference(value = "booking-payment-get")
    public Payment getPayment() {
        return this.payment ;
    }

    @OneToMany(mappedBy = "booking",cascade = CascadeType.ALL)
    public List<AdditionalGuest> additionalGuests;
    @JsonManagedReference(value = "booking-add")
    public List<AdditionalGuest> getAdditionalGuests() {
        return  this.additionalGuests;
    }

}