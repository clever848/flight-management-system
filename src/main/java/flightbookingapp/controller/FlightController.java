package flightbookingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flightbookingapp.dto.Response;
import flightbookingapp.entity.Flight;
import flightbookingapp.service.FlightService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/flight")
public class FlightController {
	@Autowired
	FlightService flightService;
	@PostMapping()
	public ResponseEntity<Response<Flight>> saveFlight(@RequestBody  Flight flight)
	{
		return new ResponseEntity<>(flightService.saveFlight(flight),HttpStatus.OK);
	}
	@GetMapping("/all")
	public ResponseEntity<Response<List<Flight>>> getAllFlight()
	{
		return new ResponseEntity<>(flightService.getAllFlight(),HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Response<Flight>> getFlightById(@PathVariable int id)
	{
		return new ResponseEntity<>(flightService.getFlightById(id),HttpStatus.OK);
	}
	@GetMapping("/source/{source}/destination/{destination}")
	public ResponseEntity<Response<List<Flight>>> getFlightBySourceAndDestination(
			@PathVariable String source,@PathVariable String destination)
	{
		return new ResponseEntity<>(flightService.getFlightBySourceAndDestination(source, destination),HttpStatus.OK);
	}
	@GetMapping("/airline/{airLine}")
	public ResponseEntity<Response<List<Flight>>> getFlightByAirLine(
			@PathVariable String airLine)
	{
		return new ResponseEntity<>(flightService.getFlightByAirLine(airLine),HttpStatus.OK);
	}
	@GetMapping("/price/{p1}/{p2}")
	public ResponseEntity<Response<List<Flight>>> getFlightBetweenPrice(
			@PathVariable double p1,@PathVariable double p2)
	{
		return new ResponseEntity<>(flightService.getFlightBetweenPrice(p1, p2),HttpStatus.OK);
	}
	@GetMapping("/cheapest/cities/{source}/{destination}")
	public ResponseEntity<Response<List<Flight>>> getCheapestFlightBwCities(
			@PathVariable String source,@PathVariable String destination)
	{
		return new ResponseEntity<>(flightService.getCheapestFlight(source, destination),HttpStatus.OK);
	}
	@GetMapping("/seat/{n}")
	public ResponseEntity<Response<List<Flight>>> getFlightMoreThanSeat(@PathVariable int n)
	{
		return new ResponseEntity<>(flightService.getFlightMoreThanAvailableSeat(n),HttpStatus.OK);
	}
	@GetMapping("/page/{pgNo}/{pgSize}/sort/{fieldName}")
	public ResponseEntity<Response<Page<Flight>>> getFlightByPaginationAndSorting(
	@PathVariable int pgNo,@PathVariable int pgSize,@PathVariable String fieldName)
	{
		return new ResponseEntity<>(flightService.getFlightByPaginationAndSorting(pgNo,
				pgSize,fieldName),HttpStatus.OK);
	}
	@PutMapping
	public ResponseEntity<Response<Flight>> updateFlight(@RequestBody Flight flight)
	{
		return new ResponseEntity<>(flightService.updateFlight(flight),HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<String>> deleteFlight(@PathVariable int id)
	{
		return new ResponseEntity<>(flightService.deleteFlight(id),HttpStatus.OK);
	}
}
