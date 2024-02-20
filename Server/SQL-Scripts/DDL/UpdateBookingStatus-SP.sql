DELIMITER $$
CREATE DEFINER=`c1SRH`@`localhost` PROCEDURE `UpdateBookingStatus`(IN `bookingId` BIGINT, IN `newStatus` VARCHAR(255))
BEGIN
    UPDATE booking
    SET booking_status = newStatus
    WHERE booking_id = bookingId;
END$$
DELIMITER ;