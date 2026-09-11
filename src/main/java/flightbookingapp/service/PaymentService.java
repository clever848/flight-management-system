package flightbookingapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flightbookingapp.dto.ModeOfPayment;
import flightbookingapp.dto.PaymentDto;
import flightbookingapp.dto.Response;
import flightbookingapp.dto.Status;
import flightbookingapp.entity.Booking;
import flightbookingapp.entity.Payment;
import flightbookingapp.exception.NotFoundException;
import flightbookingapp.repositary.BookingRepositary;
import flightbookingapp.repositary.PaymentRepository;

@Service
public class PaymentService {
	@Autowired
	PaymentRepository paymentRepo;
	@Autowired
	BookingRepositary bookingRepo;
	//get all payment
	public Response<List<PaymentDto>> getAllPayment()
	{
		List<Payment> payments = paymentRepo.findAll();
		if(payments.size()==0)
			throw new NotFoundException("no payments found");
		List<PaymentDto> paymentDtos = new ArrayList<PaymentDto>();
		for(Payment p:payments)
		{
			paymentDtos.add(PaymentDto.transferPayment(p));
		}
		Response<List<PaymentDto>> res = new Response<List<PaymentDto>>();
		return res.fetchResponse(paymentDtos);
	}
	//get payment by id
	public Response<PaymentDto> getPaymentById(int id)
	{
		Payment payment = paymentRepo.findById(id).orElseThrow(()->
		new NotFoundException("no payment found in id : "+id));
		PaymentDto paymentDto = PaymentDto.transferPayment(payment);
		Response<PaymentDto> res = new Response<PaymentDto>();
		return res.fetchResponse(paymentDto);
	}
	//change status of payment
	public Response<PaymentDto> changePaymentStatus(int id,Status status)
	{
		Payment payment = paymentRepo.findById(id).orElseThrow(()->
		new NotFoundException("no payment found with id : "+id));
		payment.setStatus(status);
		Payment savedPayment = paymentRepo.save(payment);
		PaymentDto paymentDto = PaymentDto.transferPayment(savedPayment);
		Response<PaymentDto> res = new Response<PaymentDto>();
		return res.fetchResponse(paymentDto);
	}
	//get payment by status
	public Response<List<PaymentDto>> getPaymentByStatus(Status status)
	{
		List<Payment> payments = paymentRepo.findByStatus(status);
		if(payments.size()==0)
			throw new NotFoundException("no payment found with status : "+status);
		List<PaymentDto> paymentDtos = new ArrayList<PaymentDto>();
		for(Payment p:payments)
		{
			paymentDtos.add(PaymentDto.transferPayment(p));
		}
		Response<List<PaymentDto>> res = new Response<List<PaymentDto>>();
		return res.fetchResponse(paymentDtos);
	}
	//get payment by mode of payment
	public Response<List<PaymentDto>> getPaymentByModeOfPayment(ModeOfPayment modeOfPayment)
	{
		List<Payment> payments = paymentRepo.findByModeOfPayment(modeOfPayment);
		if(payments.size() == 0)
			throw new NotFoundException("no payment found with mode of payment : "+modeOfPayment);
		List<PaymentDto> paymentDtos = new ArrayList<PaymentDto>();
		for(Payment p:payments)
		{
			paymentDtos.add(PaymentDto.transferPayment(p));
		}
		Response<List<PaymentDto>> res = new Response<List<PaymentDto>>();
		return res.fetchResponse(paymentDtos);
	}
	//get payment details of the booking
	public Response<PaymentDto> getPaymentByBookingId(int id)
	{
		Booking booking = bookingRepo.findById(id).orElseThrow(()->
		new NotFoundException("no booking found with id : "+id));
		Payment payment = booking.getPayment();
		PaymentDto paymentDto = PaymentDto.transferPayment(payment);
		Response<PaymentDto> res = new Response<PaymentDto>();
		return res.fetchResponse(paymentDto);
	}
	
}
