package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.AppointmentRequest;
import site.smartbase.dto.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    @Transactional
    AppointmentResponse addAppointment(AppointmentRequest appointmentRequest, Long userId, Long candidateId, Long vacancyId);

    List<AppointmentResponse> getAllAppointmentsForCandidate(Long candidateId);
}
