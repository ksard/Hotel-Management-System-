package com.example.hotelmanagementsystem.repositories;

import com.example.hotelmanagementsystem.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
