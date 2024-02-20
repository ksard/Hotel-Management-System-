package com.example.hotelmanagementsystem.repositories;
import com.example.hotelmanagementsystem.dto.FetchedBookingData;
import com.example.hotelmanagementsystem.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long>{
    @Modifying
    @Query(value = "CALL UpdateBookingStatus(:bookingId, :newStatus)", nativeQuery = true)
    int updateBookingStatus(@Param("bookingId")Long bookingId, @Param("newStatus") String newStatus);
    @Modifying
    @Query(value = "CALL UpdateOptedServices(:inBookingId, :inOptedServices)", nativeQuery = true)
    int updateOptedServices(@Param("inBookingId")Long bookingId, @Param("inOptedServices") String optedServices);
    @Query(value = "CALL FetchBookings()", nativeQuery = true)
    List<FetchedBookingData> FetchBookings();

}
