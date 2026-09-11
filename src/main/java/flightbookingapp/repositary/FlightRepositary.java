package flightbookingapp.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import flightbookingapp.entity.Flight;

public interface FlightRepositary extends JpaRepository<Flight,Integer>{
	
	public List<Flight> findBySourceAndDestination(String source,String destination);
	public List<Flight> findByAirLine(String airLine);
	public List<Flight> findByPriceBetween(double p1,double p2);
	public List<Flight> findTopBySourceAndDestinationOrderByPriceAsc(String source,String destination);
	public List<Flight> findByAvailableSeatsGreaterThan(int seat);
	
}
