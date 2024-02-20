package com.example.hotelmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Payment")
@Getter
@Setter
@NoArgsConstructor
public class Payment extends  CommonInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;


    @OneToOne(mappedBy = "payment")
    private Booking booking;
    @JsonManagedReference(value = "booking-payment-set")
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    @JsonManagedReference(value = "booking-payment-get")
    public Booking getBooking() {
        return this.booking ;
    }

    @Column(name="payment_type",nullable = false, length = 25)
    private String paymentType;

    @Column(name="transaction_id",nullable = false,length = 50)
    private String transactionId;

    @Column(name="transaction_status")
    private String transactionStatus;

    @Column(name="payment_source_info",nullable = false,length = 50)
    private String paymentSourceInfo;

}
