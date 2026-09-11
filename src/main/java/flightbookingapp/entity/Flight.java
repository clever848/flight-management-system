package flightbookingapp.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer flightId;
	private String airLine;
	private String source;
	private String destination;
	private LocalDateTime departureDateTime;
	private LocalDateTime arrivalDateTime;
	private Integer totalSeats;
	private Integer availableSeats;
	private Double price;
	@JsonIgnore
	@OneToMany(mappedBy = "flight")
	private List<Booking> booking;
}
