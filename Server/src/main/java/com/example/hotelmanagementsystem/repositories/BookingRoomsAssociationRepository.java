package com.example.hotelmanagementsystem.repositories;

import com.example.hotelmanagementsystem.models.BookingRoomsAssociation;
import com.example.hotelmanagementsystem.models.RoomsAndRoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRoomsAssociationRepository extends JpaRepository<BookingRoomsAssociation, BookingRoomsAssociation.BookingRoomsAssociationId> {
}
