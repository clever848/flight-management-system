package flightbookingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flightbookingapp.dto.ModeOfPayment;
import flightbookingapp.dto.PaymentDto;
import flightbookingapp.dto.Response;
import flightbookingapp.dto.Status;
import flightbookingapp.entity.Payment;
import flightbookingapp.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	PaymentService paymentService;
	@GetMapping("/all")
	public ResponseEntity<Response<List<PaymentDto>>> getAllPayment()
	{
		return new ResponseEntity<>(paymentService.getAllPayment(),HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Response<PaymentDto>> getPaymentById(@PathVariable int id)
	{
		return new ResponseEntity<>(paymentService.getPaymentById(id),HttpStatus.OK);
	}
	@PatchMapping("{id}/status/{status}")
	public ResponseEntity<Response<PaymentDto>> changePaymentStatus(@PathVariable int id,@PathVariable Status status)
	{
		return new ResponseEntity<>(paymentService.changePaymentStatus(id, status),HttpStatus.OK);
	}
	@GetMapping("/status/{status}")
	public ResponseEntity<Response<List<PaymentDto>>> getPaymentByStatus(
			@PathVariable Status status)
	{
		return new ResponseEntity<>(paymentService.getPaymentByStatus(status),HttpStatus.OK);
	}
	@GetMapping("/modeofpayment/{modeOfPayment}")
	public ResponseEntity<Response<List<PaymentDto>>> getPaymentByModeOfPayment(
			@PathVariable ModeOfPayment modeOfPayment)
	{
		return new ResponseEntity<>(
				paymentService.getPaymentByModeOfPayment(modeOfPayment),HttpStatus.OK);
	}
	@GetMapping("/booking/{id}")
	public ResponseEntity<Response<PaymentDto>> getPaymentByBookingId(@PathVariable int id)
	{
		return new ResponseEntity<>(paymentService.getPaymentByBookingId(id),HttpStatus.OK);
	}
}
