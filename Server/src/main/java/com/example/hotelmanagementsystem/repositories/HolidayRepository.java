package com.example.hotelmanagementsystem.repositories;

import com.example.hotelmanagementsystem.models.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

}
