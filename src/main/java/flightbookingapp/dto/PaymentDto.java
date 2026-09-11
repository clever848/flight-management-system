package flightbookingapp.dto;

import java.time.LocalDateTime;

import flightbookingapp.entity.Booking;
import flightbookingapp.entity.Payment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
	private Integer id;
	private LocalDateTime paymentDateTime;
	private Double amount;
	private ModeOfPayment modeOfPayment;
	private Status status;
	private BookingDto bookingDto;
	public static PaymentDto transferPayment(Payment payment)
	{
		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setAmount(payment.getAmount());
		paymentDto.setId(payment.getId());
		paymentDto.setModeOfPayment(payment.getModeOfPayment());
		paymentDto.setPaymentDateTime(payment.getPaymentDateTime());
		paymentDto.setStatus(payment.getStatus());
		paymentDto.setBookingDto(BookingDto.transferBooking(payment.getBooking()));
		return paymentDto;
	}
}
