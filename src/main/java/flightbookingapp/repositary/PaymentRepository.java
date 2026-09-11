package flightbookingapp.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import flightbookingapp.dto.ModeOfPayment;
import flightbookingapp.dto.Status;
import flightbookingapp.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Integer>{
	public List<Payment> findByStatus(Status status);
	public List<Payment> findByModeOfPayment(ModeOfPayment modeOfPayment);
	public Payment findByBookingId(int id);
}
