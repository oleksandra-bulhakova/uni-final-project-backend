package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Appointment;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
}
