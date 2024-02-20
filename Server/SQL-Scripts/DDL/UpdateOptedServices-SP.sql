DELIMITER $$
CREATE DEFINER=`c1SRH`@`localhost` PROCEDURE `UpdateOptedServices`(IN `inBookingId` BIGINT, IN `inOptedServices` VARCHAR(50))
    NO SQL
BEGIN
UPDATE booking SET opted_services=inOptedServices WHERE booking_id=inBookingId;
END$$
DELIMITER ;