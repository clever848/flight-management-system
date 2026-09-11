package flightbookingapp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flightbookingapp.dto.BookingDto;
import flightbookingapp.dto.Response;
import flightbookingapp.dto.Status;
import flightbookingapp.entity.Booking;
import flightbookingapp.entity.Flight;
import flightbookingapp.entity.Passanger;
import flightbookingapp.entity.Payment;
import flightbookingapp.exception.InvalidException;
import flightbookingapp.exception.NotFoundException;
import flightbookingapp.repositary.BookingRepositary;
import flightbookingapp.repositary.FlightRepositary;
import flightbookingapp.repositary.PassangerRepository;
import flightbookingapp.repositary.PaymentRepository;

@Service
public class BookingService {
	@Autowired
	BookingRepositary bookingRepo;
	@Autowired
	FlightRepositary flightRepo;
	@Autowired
	PaymentRepository paymentRepo;
	@Autowired
	PassangerRepository passangerRepo;
	//save booking
	public Response<BookingDto> saveBooking(Booking booking)
	{
		validateBooking(booking);
//		flight.setAvailableSeats(flight.getAvailableSeats()-1);
		booking.setBookingDateTime(LocalDateTime.now());
		booking.setStatus(Status.SUCCESS);
		Booking savedBooking = bookingRepo.save(booking);
		BookingDto bookingDto = BookingDto.transferBooking(savedBooking);
		Response<BookingDto> res = new Response<BookingDto>();
		return res.fetchResponse(bookingDto);
	}
	public void validateBooking(Booking booking)
	{
		if(booking.getFlight() == null || booking.getFlight().getFlightId() == null)
			throw new NotFoundException("every booking needs flight id");
		Integer flightId = booking.getFlight().getFlightId();
		Flight flight =  flightRepo.findById(flightId).orElseThrow(()->
		new NotFoundException("no flights found with the id : "+flightId));
		booking.setFlight(flight);
		if(booking.getPassangers() == null)
			throw new NotFoundException("booking needs atleast 1 passanger");
		List<Passanger> passangers = booking.getPassangers();
		Set<Long> contactCheck = new HashSet<Long>();
		Set<Integer> seatCheck = new HashSet<Integer>();
		for(Passanger p: passangers)
		{
			if(contactCheck.contains(p.getContactNumber()))
				throw new InvalidException("duplicate contact numbers");
			else {
				if((p.getContactNumber()+"").length()!=10)
				{
					System.out.println((p.getContactNumber()+"").length()+" "+p.getContactNumber());
					throw new InvalidException("contact number must be 10");					
				}
				contactCheck.add(p.getContactNumber());
			}
			if(seatCheck.contains(p.getSeatNo()))
				throw new InvalidException("duplicate seats selected");
			else {
				if(p.getSeatNo()>flight.getTotalSeats()|| p.getSeatNo()<=0)
					throw new InvalidException("invalid seat selection");
				if(passangerRepo.existsBySeatNoAndBookingFlightFlightId
						(p.getSeatNo(),flight.getFlightId()))
					throw new InvalidException("seat already reserved");
				seatCheck.add(p.getSeatNo());
			}
			p.setBooking(booking);
		}
		//should handle available seat
		flight.setAvailableSeats(flight.getAvailableSeats()-passangers.size());
		Payment payment = booking.getPayment();
		if(payment == null)
			throw new NotFoundException("payment not deteteced");
		payment.setBooking(booking);
		payment.setStatus(Status.SUCCESS);
		payment.setAmount(flight.getPrice()*passangers.size());
		payment.setPaymentDateTime(LocalDateTime.now());
	}
	//get all bookings
	public Response<List<BookingDto>> getAllBookings()
	{
		List<Booking> l = bookingRepo.findAll();
		if(l.size()==0)
			throw new NotFoundException("no bookings found");
		List<BookingDto> list = new ArrayList<BookingDto>();
		for(Booking b:l)
		{
			list.add(BookingDto.transferBooking(b));
		}
		Response<List<BookingDto>> res = new Response<List<BookingDto>>();
		return res.fetchResponse(list);
	}
	//get booking by id
	public Response<BookingDto> getBookingById(int id)
	{
		Booking booking = bookingRepo.findById(id).orElseThrow(()->
		new NotFoundException("no booking found with id "+id));
		BookingDto bookingDto = BookingDto.transferBooking(booking);
		Response<BookingDto> res = new Response<BookingDto>();
		return res.fetchResponse(bookingDto);
	}
	//find bookings by flight id
	public Response<List<BookingDto>> getBookingsByFlightId(int id)
	{
		List<Booking> l = bookingRepo.findByFlightFlightId(id);
		if(l.size()==0)
			throw new NotFoundException("no booking available for flight "+id);
		List<BookingDto> bookingDtos = new ArrayList<BookingDto>();
		for(Booking b:l)
		{
			bookingDtos.add(BookingDto.transferBooking(b));
		}
		Response<List<BookingDto>> res = new Response<List<BookingDto>>();
		return res.fetchResponse(bookingDtos);
	}
	//find booking based on date
	public Response<List<BookingDto>> getBookingByDate(LocalDate date)
	{
		LocalDateTime start = date.atStartOfDay();
		LocalDateTime end = date.plusDays(1).atStartOfDay();
		List<Booking> l = bookingRepo.findByBookingDateTimeBetween(start, end);
		if(l.size()==0) 
			throw new NotFoundException("no bookings found in date : "+date);
		List<BookingDto> bookingDtos = new ArrayList<BookingDto>();
		for(Booking b:l)
		{
			bookingDtos.add(BookingDto.transferBooking(b));
		}
		Response<List<BookingDto>> res = new Response<List<BookingDto>>();
		return res.fetchResponse(bookingDtos);
	}
	//find by status
	public Response<List<BookingDto>> getBookingByStatus(Status status)
	{
		List<Booking> bookings = bookingRepo.findByStatus(status);
		if(bookings.size()==0) 
			throw new NotFoundException("no bookings found in status : "+status);
		List<BookingDto> bookingDtos = new ArrayList<BookingDto>();
		for(Booking b:bookings)
		{
			bookingDtos.add(BookingDto.transferBooking(b));
		}
		Response<List<BookingDto>> res = new Response<List<BookingDto>>();
		return res.fetchResponse(bookingDtos);
	}
	//find by passanger id
	public Response<List<BookingDto>> getBookingHistoryOfPassanger(Long contactNo)
	{
		List<Booking> bookings = bookingRepo.findByPassangersContactNumber(contactNo);
		if(bookings.size()==0)
			throw new NotFoundException("no bookings history by passanger contact number "+contactNo);
		List<BookingDto> bookingDtos = new ArrayList<BookingDto>();
		for(Booking b:bookings)
		{
			bookingDtos.add(BookingDto.transferBooking(b));
		}
		Response<List<BookingDto>> res = new Response<List<BookingDto>>();
		return res.fetchResponse(bookingDtos);
	}
	
	//get all passangers in a booking
//	public Response<List<Passanger>> getAllPassangersInABooking(int id)
//	{
//		Booking booking = bookingRepo.findById(id).orElseThrow(()->
//		new NotFoundException("no booking found in the id : "+id));
//		List<Passanger> passangers = booking.getPassangers();
//		Response<List<Passanger>> res = new Response<List<Passanger>>();
//		return res.fetchResponse(passangers);
//	}
	//delete booking
	//cant cancel already cancelled booking
	public Response<String> deleteBooking(int id)
	{
		Booking booking = bookingRepo.findById(id).orElseThrow(()->
		new NotFoundException("no bookings found in the id : "+id));
		Flight flight = booking.getFlight();
		Payment payment = booking.getPayment();
		System.out.println("available seat : "+flight.getAvailableSeats());
		flight.setAvailableSeats(flight.getAvailableSeats()+booking.getPassangers().size());
		System.out.println("size "+booking.getPassangers().size());
		payment.setStatus(Status.REFUNDED);
		flightRepo.save(flight);
		booking.setStatus(Status.CANCELED);
		bookingRepo.save(booking);
		Response<String> res = new Response<String>();
		return res.deleteResponse();
	}
	//get total amount paid for the flight
	public Response<Double> getTotalAmountonFlight(int id)
	{
		List<Booking> bookingDtos = bookingRepo.findByFlightFlightId(id);
		if(bookingDtos.size() == 0)
			throw new NotFoundException("no bookings found with flight id "+id);
		double sum = 0;
		for(Booking b:bookingDtos)
		{
			sum+=b.getPayment().getAmount();
		}
		Response<Double> res = new Response<Double>();
		return res.fetchResponse(sum);
	}
}
