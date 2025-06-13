package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.AppointmentRequest;
import site.smartbase.dto.AppointmentResponse;
import site.smartbase.dto.AppointmentUpdate;
import site.smartbase.entity.*;
import site.smartbase.enums.AppointmentStatus;
import site.smartbase.enums.AppointmentType;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.*;
import site.smartbase.service.AppointmentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final UserRepo userRepo;
    private final CandidateRepo candidateRepo;
    private final VacancyRepo vacancyRepo;
    private final ModelMapper modelMapper;
    private final CommentRepo commentRepo;

    @Transactional
    @Override
    public AppointmentResponse addAppointment(AppointmentRequest appointmentRequest, Long userId, Long candidateId, Long vacancyId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found"));
        Candidate candidate = candidateRepo.findById(candidateId).orElseThrow(() -> new NotFoundException("Candidate Not Found"));
        Vacancy vacancy = vacancyRepo.findById(vacancyId).orElseThrow(() -> new NotFoundException("Vacancy Not Found"));

        Appointment appointment = modelMapper.map(appointmentRequest, Appointment.class);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setHost(user);
        appointment.setParticipant(candidate);
        appointment.setVacancy(vacancy);
        appointmentRepo.save(appointment);

        Comment comment = new Comment();
        comment.setDate(LocalDate.now());
        comment.setAuthor(user);
        comment.setAddressee(candidate);
        comment.setDescription("Додано подію " + appointment.getType().toString());
        commentRepo.save(comment);

        candidate.getComments().add(comment);

        return modelMapper.map(appointment, AppointmentResponse.class);
    }

    @Override
    public List<AppointmentResponse> getAllAppointmentsForCandidate(Long candidateId) {
        candidateRepo.findById(candidateId).orElseThrow(() -> new NotFoundException("Candidate Not Found"));

        List<Appointment> appointments = appointmentRepo.findAllByParticipantId(candidateId);
        List<AppointmentResponse> responses = new ArrayList<>();
        if (!appointments.isEmpty()) {
            responses = appointments.stream().map(appointment -> modelMapper.map(appointment, AppointmentResponse.class)).toList();
        }
        return responses;
    }

    @Transactional
    @Override
    public AppointmentResponse updateAppointment(AppointmentUpdate appointmentUpdate, Long userId, Long candidateId,
                                                 Long vacancyId, Long appointmentId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found"));
        Candidate candidate = candidateRepo.findById(candidateId).orElseThrow(() -> new NotFoundException("Candidate Not Found"));
        vacancyRepo.findById(vacancyId).orElseThrow(() -> new NotFoundException("Vacancy Not Found"));
        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(() -> new NotFoundException("Appointment Not Found"));

        appointment.setStatus(AppointmentStatus.valueOf(appointmentUpdate.getAppointmentStatus()));
        appointment.setDate(appointmentUpdate.getDate());
        appointment.setType(AppointmentType.valueOf(appointmentUpdate.getAppointmentType()));

        Comment comment = new Comment();
        comment.setDate(LocalDate.now());
        comment.setAuthor(user);
        comment.setAddressee(candidate);
        comment.setDescription("Оновлено подію " + appointment.getType().toString());
        commentRepo.save(comment);

        candidate.getComments().add(comment);

        return modelMapper.map(appointment, AppointmentResponse.class);
    }

    @Transactional
    @Override
    public void deleteAppointment(Long appointmentId, Long userId, Long candidateId) {
        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(() -> new NotFoundException("Appointment Not Found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found"));
        Candidate candidate = candidateRepo.findById(candidateId).orElseThrow(() -> new NotFoundException("Candidate Not Found"));
        String appointmentType = appointment.getType().toString();

        appointmentRepo.deleteById(appointmentId);

        Comment comment = new Comment();
        comment.setDate(LocalDate.now());
        comment.setAuthor(user);
        comment.setAddressee(candidate);
        comment.setDescription("Видалено подію " + appointmentType);
        commentRepo.save(comment);

        candidate.getComments().add(comment);
    }
}
