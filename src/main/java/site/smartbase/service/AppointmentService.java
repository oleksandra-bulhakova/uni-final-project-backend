package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.AppointmentRequest;
import site.smartbase.dto.AppointmentResponse;
import site.smartbase.dto.AppointmentUpdate;

import java.util.List;

public interface AppointmentService {
    @Transactional
    AppointmentResponse addAppointment(AppointmentRequest appointmentRequest, Long userId, Long candidateId, Long vacancyId);

    List<AppointmentResponse> getAllAppointmentsForCandidate(Long candidateId);

    @Transactional
    AppointmentResponse updateAppointment(AppointmentUpdate appointmentUpdate, Long userId, Long candidateId,
                                          Long vacancyId, Long appointmentId);

    @Transactional
    void deleteAppointment(Long appointmentId, Long userId, Long candidateId);
}
