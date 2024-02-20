package com.example.hotelmanagementsystem.repositories;

import com.example.hotelmanagementsystem.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(value="CALL FindFreeRoomsByType(:checkInDate, :checkOutDate)", nativeQuery = true)
    List<Object[]> findFreeRoomsByType(@Param("checkInDate") String checkInDate, @Param("checkOutDate") String checkOutDate);

    }
