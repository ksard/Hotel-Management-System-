package com.example.hotelmanagementsystem.repositories;

import com.example.hotelmanagementsystem.models.AdditionalGuest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface AdditionalGuestRepository extends JpaRepository<AdditionalGuest,Long> {
}
