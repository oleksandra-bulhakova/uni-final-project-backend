package site.smartbase.mapper;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.AppointmentRequest;
import site.smartbase.entity.Appointment;
import site.smartbase.enums.AppointmentType;

@Component
public class AppointmentMapper extends AbstractConverter<AppointmentRequest, Appointment> {
    @Override
    protected Appointment convert(AppointmentRequest appointmentRequest) {
        return Appointment.builder()
                .date(appointmentRequest.getDate())
                .type(AppointmentType.valueOf(appointmentRequest.getAppointmentType()))
                .build();
    }
}
