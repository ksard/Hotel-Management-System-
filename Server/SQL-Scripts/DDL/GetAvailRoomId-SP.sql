DELIMITER $$
CREATE DEFINER=`c1SRH`@`localhost` PROCEDURE `GetAvailRoomId`(IN `inCheckIn` DATE, IN `inCheckOut` DATE, IN `roomTypeId` VARCHAR(30))
    NO SQL
BEGIN
SELECT rooms.room_type_id, rooms.room_id
FROM rooms
WHERe rooms.room_id NOT IN (
    SELECT brass.room_id FROM booking_rooms_association brass, booking b
    WHERE  brass.booking_id=b.booking_id and
           b.checkin_date BETWEEN inCheckIn and inCheckOut
       OR b.checkout_date BETWEEN inCheckIn and inCheckOut
)
  AND FIND_IN_SET(rooms.room_type_id, roomTypeId)>0;
END$$
DELIMITER ;