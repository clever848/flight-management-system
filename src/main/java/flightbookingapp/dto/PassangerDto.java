package flightbookingapp.dto;

import flightbookingapp.entity.Booking;
import flightbookingapp.entity.Passanger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassangerDto {
	private Integer id;
	private String name;
	private Integer age;
	private Gender gender;
	private Integer seatNo;
	private Long contactNumber;
	private BookingDto bookingDto;
	public static PassangerDto transferPassanger(Passanger p)
	{
		PassangerDto pd = new PassangerDto();
		pd.setAge(p.getAge());
		pd.setContactNumber(p.getContactNumber());
		pd.setGender(p.getGender());
		pd.setSeatNo(p.getSeatNo());
		pd.setId(p.getId());
		pd.setName(p.getName());
		pd.setBookingDto(BookingDto.transferBooking(p.getBooking()));
		return pd;
	}
}
