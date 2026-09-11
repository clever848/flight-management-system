package flightbookingapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flightbookingapp.dto.Gender;
import flightbookingapp.dto.PassangerDto;
import flightbookingapp.dto.Response;
import flightbookingapp.service.PassangerService;

@RestController
@RequestMapping("/passanger")
public class PassangerController {
	@Autowired
	PassangerService passangerService;
	@GetMapping("/all")
	public ResponseEntity<Response<List<PassangerDto>>> getAllPassanger()
	{
		return new ResponseEntity<>(passangerService.getAllPassanger(),HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Response<PassangerDto>> getPassangerById(@PathVariable int id)
	{
		return new ResponseEntity<>(passangerService.getPassangerById(id),HttpStatus.OK);
	}
	@GetMapping("/contactnumber/{contactNo}")
	public ResponseEntity<Response<PassangerDto>> getPassangerByContactNumber(
			@PathVariable Long contactNo)
	{
		return new ResponseEntity<>(passangerService.getPassangerByContactNumber(contactNo),HttpStatus.OK);
	}
	@GetMapping("/gender/{gender}")
	public ResponseEntity<Response<List<PassangerDto>>> getPassangerByGender(@PathVariable Gender gender)
	{
		return new ResponseEntity<>(passangerService.getPassangerByGender(gender),HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<String>> deletePassangerById(@PathVariable int id)
	{
		return new ResponseEntity<>(passangerService.deletePassangerById(id),HttpStatus.OK);
	}
	@GetMapping("/flight/{id}")
	public ResponseEntity<Response<List<PassangerDto>>> getPassangerByFlightId(@PathVariable int id)
	{
		return new ResponseEntity<>(passangerService.getPassangerByFlight(id),HttpStatus.OK);
	}
	@PatchMapping("/details/{id}")
	public ResponseEntity<Response<PassangerDto>> updatePassangerInfo(@PathVariable int id,
			@RequestBody Map<String, Object> m)
	{
		return new ResponseEntity<>(passangerService.updatePassangerInfo(id, m),HttpStatus.OK);
	}
}

