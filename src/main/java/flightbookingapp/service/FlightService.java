package flightbookingapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import flightbookingapp.dto.Response;
import flightbookingapp.entity.Flight;
import flightbookingapp.exception.NotFoundException;
import flightbookingapp.repositary.FlightRepositary;

@Service
public class FlightService {
	@Autowired
	FlightRepositary flightRepo;
	//save flight
	public Response<Flight> saveFlight(Flight flight)
	{
		validateFlight(flight);
		Flight savedFlight = flightRepo.save(flight);
		Response<Flight> res = new Response<Flight>();
		return res.fetchResponse(savedFlight);
	}
	public void validateFlight(Flight flight)
	{
		if(flight.getAvailableSeats() == null)
			throw new NotFoundException("flight must need available seats");
		if(flight.getPrice() == null)
			throw new NotFoundException("flight must need price");
	}
	//get all flight
	public Response<List<Flight>> getAllFlight()
	{
		List<Flight> l = flightRepo.findAll();
		if(l.size()>0)
		{
			Response<List<Flight>> res = new Response<List<Flight>>();
			return res.fetchResponse(l);
		}
		throw new NotFoundException("no flights found");
	}
	//get flight by id
	public Response<Flight> getFlightById(int id)
	{
		Flight flight = flightRepo.findById(id).orElseThrow(()->
		new NotFoundException("no flight found with id "+id));
		Response<Flight> res = new Response<Flight>();
		return res.fetchResponse(flight);
	}
	//get flight by source and destination
	public Response<List<Flight>> getFlightBySourceAndDestination(String source
			,String destination)
	{
		List<Flight> l = flightRepo.findBySourceAndDestination(source, destination);
		if(l.size()>0)
		{
			Response<List<Flight>> res = new Response<List<Flight>>();
			return res.fetchResponse(l);
		}
		throw new NotFoundException("no flights available between source : "+source+
				" destination : "+destination);
	}
	//get flight by airLine
	public Response<List<Flight>> getFlightByAirLine(String airLine)
	{
		List<Flight> l = flightRepo.findByAirLine(airLine);
		if(l.size()>0)
		{
			Response<List<Flight>> res = new Response<List<Flight>>();
			return res.fetchResponse(l);
		}
		throw new NotFoundException("no flight "
				+ "found with airline : "+airLine);
	}
	//get flight by price range
	public Response<List<Flight>> getFlightBetweenPrice(double p1,double p2)
	{
		List<Flight> l = flightRepo.findByPriceBetween(p1, p2);
		if(l.size()>0)
		{
			Response<List<Flight>> res = new Response<List<Flight>>();
			return res.fetchResponse(l);
		}
		throw new NotFoundException("no flights found between price "+p1+" "+p2);
	}
	//find cheapest flight between source and destination
	public Response<List<Flight>> getCheapestFlight(String source,String destination)
	{
		List<Flight> l = flightRepo.findTopBySourceAndDestinationOrderByPriceAsc(source, destination);
		if(l.size()==0) throw new NotFoundException("no flights between "+source+" "+
		destination);
		Response<List<Flight>> res = new Response<List<Flight>>();
		return res.fetchResponse(l);
	}
	//find flight that has more than x seat
	public Response<List<Flight>> getFlightMoreThanAvailableSeat(int n)
	{
		List<Flight> l = flightRepo.findByAvailableSeatsGreaterThan(n);
		if(l.size()==0) throw new NotFoundException("no flights available more than "+n+" seat");
		Response<List<Flight>> res = new Response<List<Flight>>();
		return res.fetchResponse(l);
	}
	//find flight by pagination and sorting
	public Response<Page<Flight>> getFlightByPaginationAndSorting(int pgNo,
			int pgSize,String fieldName)
	{
		Page<Flight> page = flightRepo.findAll(PageRequest.of(pgNo, pgSize,Sort.by(fieldName).ascending()));
		if(page.isEmpty()) throw new NotFoundException("no flights found in page "+pgNo);
		Response<Page<Flight>> res = new Response<Page<Flight>>();
		return res.fetchResponse(page);
	}
	//fully update flight
	public Response<Flight> updateFlight(Flight flight)
	{
		Flight updateFlight = flightRepo.findById(flight.getFlightId()).orElseThrow(()->
		new NotFoundException("no flight found with id "+flight.getFlightId()));
		Response<Flight> res = new Response<Flight>();
		return res.fetchResponse(flightRepo.save(flight));
	}
	//delete flight
	public Response<String> deleteFlight(int id)
	{
		Flight flight = flightRepo.findById(id).orElseThrow(()->
		new NotFoundException("no flights found with id "+id));
		
		if(flight.getBooking().size() == 0)
		{				
			flightRepo.deleteById(id);
			Response<String> res = new Response<String>();
			return res.deleteResponse();
		}
		throw new RuntimeException("this flight has "+flight.getBooking().size()+
				" bookings so it can't be deleted");
	}
}
