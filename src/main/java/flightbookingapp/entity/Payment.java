package flightbookingapp.entity;

import java.time.LocalDateTime;

import flightbookingapp.dto.ModeOfPayment;
import flightbookingapp.dto.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDateTime paymentDateTime;
	private Double amount;
	@Enumerated(EnumType.STRING)
	private ModeOfPayment modeOfPayment;
	@Enumerated(EnumType.STRING)
	private Status status;
	@JoinColumn(name = "booking_id")
	@OneToOne
	private Booking booking;
}
