package flightbookingapp.repositary;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import flightbookingapp.dto.Gender;
import flightbookingapp.entity.Passanger;

public interface PassangerRepository extends JpaRepository<Passanger,Integer>{
	public Optional<Passanger> findFirstByContactNumber(Long contactNo);
	public Boolean existsBySeatNoAndBookingFlightFlightId(int seatNo,int flightId);
	public List<Passanger> findByGender(Gender gender);
	public List<Passanger> findByBookingFlightFlightId(int id);
}
