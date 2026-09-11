package flightbookingapp.dto;

import java.time.LocalDateTime;

import flightbookingapp.entity.Booking;
import flightbookingapp.entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BookingDto {
	private Integer id;
	private LocalDateTime bookingDateTime;
	private Status status;
	private Flight flight;
	public static BookingDto transferBooking(Booking booking)
	{
		BookingDto bookingDto = new BookingDto();
		bookingDto.setId(booking.getId());
		bookingDto.setFlight(booking.getFlight());
		bookingDto.setStatus(booking.getStatus());
		bookingDto.setBookingDateTime(booking.getBookingDateTime());
		return bookingDto;
	}
}
