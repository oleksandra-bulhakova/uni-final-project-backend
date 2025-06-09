package site.smartbase.mapper;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.AppointmentResponse;
import site.smartbase.dto.CandidateResponse;
import site.smartbase.dto.UserResponse;
import site.smartbase.entity.Appointment;

@Component
public class AppointmentResponseMapper extends AbstractConverter<Appointment, AppointmentResponse> {
    @Override
    protected AppointmentResponse convert(Appointment appointment) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(appointment.getHost().getId());
        userResponse.setFirstName(appointment.getHost().getFirstName());
        userResponse.setLastName(appointment.getHost().getLastName());

        CandidateResponse candidateResponse = new CandidateResponse();
        candidateResponse.setId(appointment.getParticipant().getId());
        candidateResponse.setFirstName(appointment.getParticipant().getFirstName());
        candidateResponse.setLastName(appointment.getParticipant().getLastName());

        return AppointmentResponse.builder()
                .id(appointment.getId())
                .date(appointment.getDate())
                .status(appointment.getStatus().toString())
                .type(appointment.getType().toString())
                .user(userResponse)
                .candidate(candidateResponse)
                .vacancyName(appointment.getVacancy().getName())
                .build();
    }
}
