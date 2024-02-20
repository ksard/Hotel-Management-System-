DELIMITER $$
CREATE DEFINER=`c1SRH`@`localhost` PROCEDURE `ListRoomTypes`()
BEGIN
SELECT room_types.room_type_id, room_types.base_price, room_types.floor, room_types.included_services, room_types.surge_fee_afteraweek, room_types.surge_fee_beforeaweek, room_types.surge_fee_current_day, room_types.surge_fee_ongoing_week, room_types.type, room_types.view, room_types.weekend_surge_fee
FROM room_types;
END$$
DELIMITER ;