DELIMITER $$
CREATE DEFINER=c1SRH@localhost PROCEDURE FetchBookings()
    NO SQL
BEGIN
SELECT b.booking_id, b.booking_status, b.booking_date, b.checkin_date, b.checkout_date, b.num_of_guest, b.guest_id , p.email,p.first_name,p.last_name, p.phone_no , b.total_price, b.opted_services, COUNT(brass.booking_id) AS room_count
FROM booking b
         JOIN people p
              ON b.guest_id=p.person_id
         JOIN booking_rooms_association brass ON b.booking_id = brass.booking_id
WHERE b.booking_status in ('Confirmed', 'CheckedIn')
GROUP BY b.booking_id;
END$$
DELIMITER;