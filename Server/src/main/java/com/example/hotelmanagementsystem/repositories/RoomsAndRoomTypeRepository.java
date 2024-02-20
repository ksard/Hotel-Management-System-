package com.example.hotelmanagementsystem.repositories;

import com.example.hotelmanagementsystem.models.Room;
import com.example.hotelmanagementsystem.models.RoomsAndRoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomsAndRoomTypeRepository extends JpaRepository<RoomsAndRoomType, RoomsAndRoomType> {
    @Query(value="CALL GetAvailRoomId(:checkInDate, :checkOutDate, :roomTypeId)", nativeQuery = true)
    List<RoomsAndRoomType> checkRoomAvailability(@Param("checkInDate") String checkInDate, @Param("checkOutDate") String checkOutDate, @Param("roomTypeId") String roomTypeIds);

}
