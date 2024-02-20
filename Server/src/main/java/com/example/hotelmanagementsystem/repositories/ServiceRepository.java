package com.example.hotelmanagementsystem.repositories;

import com.example.hotelmanagementsystem.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
