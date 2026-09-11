package flightbookingapp.repositary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import flightbookingapp.dto.Status;
import flightbookingapp.entity.Booking;

public interface BookingRepositary extends JpaRepository<Booking,Integer>{
	public List<Booking> findByFlightFlightId(int id);
	public List<Booking> findByBookingDateTimeBetween(LocalDateTime start,LocalDateTime end);
	public List<Booking> findByStatus(Status status);
	public List<Booking> findByPassangersContactNumber(Long contactNo);
}
