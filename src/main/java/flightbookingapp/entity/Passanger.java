package flightbookingapp.entity;

import flightbookingapp.dto.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Passanger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Integer age;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private Integer seatNo;
	private Long contactNumber;
	@JoinColumn(name = "booking_id")
	@ManyToOne
	private Booking booking;
	@Override
	public String toString() {
		return "Passanger [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", seatNo=" + seatNo
				+ ", contactNumber=" + contactNumber ;
	}
	
}
