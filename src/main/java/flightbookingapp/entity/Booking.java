package flightbookingapp.entity;

import java.time.LocalDateTime;
import java.util.List;


import flightbookingapp.dto.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDateTime bookingDateTime;
	@Enumerated(EnumType.STRING)
	private Status status;
	@JoinColumn(name = "flight_id")
	@ManyToOne
	private Flight flight;
	@OneToMany(mappedBy = "booking",cascade = CascadeType.ALL)
	private List<Passanger> passangers;
	@OneToOne(mappedBy = "booking",cascade = CascadeType.ALL)
	private Payment payment;
}
