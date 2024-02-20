package com.example.hotelmanagementsystem.repositories;
import com.example.hotelmanagementsystem.models.Guest;
import com.example.hotelmanagementsystem.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    Guest findByEmail(String email);
    Boolean existsByEmail(String email);
}
