DELIMITER $$
CREATE DEFINER=`c1SRH`@`localhost` PROCEDURE `FindFreeRoomsByType`(IN `inCheckIn` DATE, IN `inCheckOut` DATE)
    NO SQL
BEGIN
SELECT room_types.type, room_types.view, COUNT(DISTINCT rooms.room_id) AS number_of_rooms FROM room_types
LEFT JOIN rooms
ON room_types.room_type_id = rooms.room_type_id
WHERe rooms.room_id NOT IN (
    SELECT DISTINCT brass.room_id FROM booking_rooms_association brass, booking b
	WHERE b.checkin_date BETWEEN inCheckIn and inCheckOut
	OR b.checkout_date BETWEEN inCheckIn and inCheckOut
)
GROUP BY room_types.type, room_types.view;
END$$
DELIMITER ;