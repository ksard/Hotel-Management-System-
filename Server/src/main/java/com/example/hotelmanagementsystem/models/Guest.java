package com.example.hotelmanagementsystem.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="Guests")
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "person_id")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "personId"
)
//@ApiModel(description = "This table holds cloud vendor information.")

public class Guest extends Person{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public Long guestId;
    @JsonIgnore
    @OneToMany(mappedBy = "guest")
    private List<Booking> booking;

    public void setBooking(List<Booking> booking) {
        this.booking = booking;

    }

}
