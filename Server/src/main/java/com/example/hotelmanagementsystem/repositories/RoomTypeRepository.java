package com.example.hotelmanagementsystem.repositories;

import com.example.hotelmanagementsystem.models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    @Query(value = "CALL ListRoomTypes()", nativeQuery = true)
    List<Object[]> listAllRoomTypes();
}
