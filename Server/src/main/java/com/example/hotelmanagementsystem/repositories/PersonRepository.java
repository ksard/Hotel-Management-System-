package com.example.hotelmanagementsystem.repositories;
import com.example.hotelmanagementsystem.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByEmail(String email);
    Boolean existsByEmail(String email);
}
