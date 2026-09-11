package flightbookingapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flightbookingapp.dto.BookingDto;
import flightbookingapp.dto.Response;
import flightbookingapp.dto.Status;
import flightbookingapp.entity.Booking;
import flightbookingapp.entity.Passanger;
import flightbookingapp.service.BookingService;
@RestController
@RequestMapping("/booking")
public class BookingController {
	@Autowired
	BookingService bookingService;
	@PostMapping
	public ResponseEntity<Response<BookingDto>> saveBooking(@RequestBody Booking booking)
	{
		return new ResponseEntity<>(bookingService.saveBooking(booking),HttpStatus.OK);
	}
	@GetMapping("/all")
	public ResponseEntity<Response<List<BookingDto>>> getAllBooking()
	{
		return new ResponseEntity<>(bookingService.getAllBookings(),HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Response<BookingDto>> getBokingById(@PathVariable int id)
	{
		return new ResponseEntity<>(bookingService.getBookingById(id),HttpStatus.OK);
	}
	@GetMapping("/flight/{id}")
	public ResponseEntity<Response<List<BookingDto>>> getBookingByFlightIf(
			@PathVariable int id)
	{
		return new ResponseEntity<>(bookingService.getBookingsByFlightId(id),HttpStatus.OK);
	}
	@GetMapping("/date/{date}")
	public ResponseEntity<Response<List<BookingDto>>> getBookingsByDate(@PathVariable LocalDate date)
	{
		return new ResponseEntity<>(bookingService.getBookingByDate(date),HttpStatus.OK);
	}
	@GetMapping("/status/{status}")
	public ResponseEntity<Response<List<BookingDto>>> getBookingByStatus(@PathVariable Status status)
	{
		return new ResponseEntity<>(bookingService.getBookingByStatus(status),HttpStatus.OK);
	}
	@GetMapping("/passanger/history/{contactNo}")
	public ResponseEntity<Response<List<BookingDto>>> getBookingHistoryOfPassanger(@PathVariable Long contactNo)
	{
		return new ResponseEntity<>(bookingService.getBookingHistoryOfPassanger(contactNo),HttpStatus.OK);
	}
//	@GetMapping("/passanger/booking/{id}")
//	public ResponseEntity<Response<List<Passanger>>> getPassangersByBookingId(@PathVariable int id)
//	{
//		return new ResponseEntity<>(bookingService.getAllPassangersInABooking(id),
//				HttpStatus.OK);
//	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<String>> deleteBooking(@PathVariable int id)
	{
		return new ResponseEntity<>(bookingService.deleteBooking(id),HttpStatus.OK);
	}
	@GetMapping("/amount/flight/{id}")
	public ResponseEntity<Response<Double>> getTotalAmountByFlightId(@PathVariable int id)
	{
		return new ResponseEntity<>(bookingService.getTotalAmountonFlight(id),HttpStatus.OK);
	}
	
}
