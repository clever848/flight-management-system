package flightbookingapp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flightbookingapp.dto.Gender;
import flightbookingapp.dto.PassangerDto;
import flightbookingapp.dto.Response;
import flightbookingapp.entity.Booking;
import flightbookingapp.entity.Flight;
import flightbookingapp.entity.Passanger;
import flightbookingapp.entity.Payment;
import flightbookingapp.exception.InvalidException;
import flightbookingapp.exception.NotFoundException;
import flightbookingapp.repositary.BookingRepositary;
import flightbookingapp.repositary.FlightRepositary;
import flightbookingapp.repositary.PassangerRepository;

@Service
public class PassangerService {
	@Autowired
	PassangerRepository passangerRepo;
	@Autowired
	FlightRepositary flightRepo;
	@Autowired
	BookingRepositary bookingRepo;
	@Autowired
	BookingService bookingService;
	//get All passanger
	public Response<List<PassangerDto>> getAllPassanger()
	{
		List<Passanger> passangers = passangerRepo.findAll();
		if(passangers.size()==0)
			throw new NotFoundException("no passanger found!");
		List<PassangerDto> passangerDtos = new ArrayList<PassangerDto>();
		for(Passanger p:passangers)
		{
			passangerDtos.add(PassangerDto.transferPassanger(p));
		}
		Response<List<PassangerDto>> res = new Response<List<PassangerDto>>();
		return res.fetchResponse(passangerDtos);
	}
	//find passange by id
	public Response<PassangerDto> getPassangerById(int id)
	{
		Passanger passanger = passangerRepo.findById(id).orElseThrow(()->
		new NotFoundException("no passanger found with id : "+id));
		PassangerDto passangerDto = PassangerDto.transferPassanger(passanger);
		Response<PassangerDto> res = new Response<PassangerDto>();
		return res.fetchResponse(passangerDto);
	}
	//find passanger by contact number
	public Response<PassangerDto> getPassangerByContactNumber(Long contactNo)
	{
		Passanger passanger = passangerRepo.findFirstByContactNumber(contactNo).
				orElseThrow(()->new NotFoundException("no passanger found with contact number : "+contactNo));
		PassangerDto passangerDto = PassangerDto.transferPassanger(passanger);
		Response<PassangerDto> res = new Response<PassangerDto>();
		return res.fetchResponse(passangerDto);
	}
	//get passanger by gender
	public Response<List<PassangerDto>> getPassangerByGender(Gender gender)
	{
		List<Passanger> passangers = passangerRepo.findByGender(gender);
		if(passangers.size()==0)
			throw new NotFoundException("no passanger found in gender : "+gender);
		List<PassangerDto> passangerDtos = new ArrayList<PassangerDto>();
		for(Passanger p:passangers)
		{
			passangerDtos.add(PassangerDto.transferPassanger(p));
		}
		Response<List<PassangerDto>> res = new Response<List<PassangerDto>>();
		return res.fetchResponse(passangerDtos);
	}
	//delete passanger by id
	public Response<String> deletePassangerById(int id)
	{
		Passanger passanger = passangerRepo.findById(id).orElseThrow(()->
		new NotFoundException("no passanger found with the id : "+id));
		Booking booking = passanger.getBooking();
		List<Passanger> passangers = booking.getPassangers();
		if(passangers.size() ==1)
		{
			passangerRepo.deleteById(id);
			return bookingService.deleteBooking(booking.getId());
		}
		Flight flight = booking.getFlight();
		flight.setAvailableSeats(flight.getAvailableSeats()+1);
		flightRepo.save(flight);
		Payment payment = booking.getPayment();
		payment.setAmount(payment.getAmount()-flight.getPrice());
		passangers.remove(passanger);
		passangerRepo.deleteById(id);
		booking.setPassangers(passangers);
		bookingRepo.save(booking);
		Response<String> res = new Response<String>();
		return res.deleteResponse();
	}
	//find by flight id
	public Response<List<PassangerDto>> getPassangerByFlight(int id)
	{
		List<Passanger> passangers = passangerRepo.findByBookingFlightFlightId(id);
		if(passangers.size()==0)
			throw new NotFoundException("no passangers found for the flight id : "+id);
		List<PassangerDto> passangerDtos = new ArrayList<PassangerDto>();
		for(Passanger p:passangers)
		{
			passangerDtos.add(PassangerDto.transferPassanger(p));
		}
		Response<List<PassangerDto>> res = new Response<List<PassangerDto>>();
		return res.fetchResponse(passangerDtos);
	}
	//validate Seat number
	public void validateSeatNo(int seatNo,Passanger passanger)
	{
		Flight flight = passanger.getBooking().getFlight();
		if(seatNo != passanger.getSeatNo() && passangerRepo.existsBySeatNoAndBookingFlightFlightId(seatNo,flight.getFlightId()))
			throw new InvalidException("seat is already reserved");
		if(seatNo>flight.getAvailableSeats() || seatNo<=0)
			throw new InvalidException("invalid seat selected");
	}
	public void validateContactNumber(Long ContactNo,Passanger passanger)
	{
		if((ContactNo+"").length()!=10)
			throw new InvalidException("contact number must be 10 digit");
		List<Passanger> passangers = passanger.getBooking().getPassangers();
		Set<Long> contactCheck = passangers.stream().map(p->p.getContactNumber()).collect(Collectors.toSet());
		for(Passanger p: passangers)
		{
			if(!ContactNo.equals(passanger.getContactNumber()) && contactCheck.contains(ContactNo))
			{
				throw new InvalidException("duplicate contact number ");
			}
		}
	}
	//update passanger info
	public Response<PassangerDto> updatePassangerInfo(int id,Map<String,Object> m)
	{
		Passanger passanger = passangerRepo.findById(id).orElseThrow(()->
		new NotFoundException("no passanger found with id : "+id));
		for(Map.Entry<String,Object> e:m.entrySet())
		{
			switch (e.getKey()) 
			{
				case "age":
					  passanger.setAge(((Integer)e.getValue()));
					break;
				case "name":
					passanger.setName(((String)e.getValue()));
					break;
				case "gender":
					passanger.setGender(((Gender)e.getValue()));
					break;
				case "seatNo":
					validateSeatNo((Integer)e.getValue(),passanger);
					passanger.setSeatNo(((Integer)e.getValue()));
					break;
				case "contactNumber":
					validateContactNumber((Long)e.getValue(),passanger);
					passanger.setContactNumber(((Long)e.getValue()));
					break;
				default:
					throw new InvalidException("invalid field");
			}
		}
		Passanger savedPassanger = passangerRepo.save(passanger);
		Response<PassangerDto> res = new Response<PassangerDto>();
		PassangerDto passangerDto = PassangerDto.transferPassanger(savedPassanger);
		return res.fetchResponse(passangerDto);
	}
}
