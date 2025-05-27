package site.smartbase.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.*;
import site.smartbase.entity.Vacancy;
import site.smartbase.repository.AppointmentRepo;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VacancyDtoMapper extends AbstractConverter<Vacancy, VacancyDto> {
    private final AppointmentRepo appointmentRepo;

    @Override
    protected VacancyDto convert(Vacancy vacancy) {
        List<TechnologyDto> technologyList = vacancy.getTechnologies() != null ?
                vacancy.getTechnologies().stream()
                        .map(technology -> TechnologyDto.builder()
                                .id(technology.getId())
                                .name(technology.getName())
                                .build()).toList() : null;

        List<VacancyStatusCount> vacancyStatusCount = appointmentRepo.countByVacancyAndStatusForVacancy(vacancy.getId());

        ClientResponse clientResponse = ClientResponse.builder()
                .id(vacancy.getClient().getId())
                .name(vacancy.getClient().getName())
                .build();

        List<UserResponse> userResponse = vacancy.getUsers().stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .imagePath(user.getImagePath())
                        .build()).toList();

        return VacancyDto.builder()
                .id(vacancy.getId())
                .name(vacancy.getName())
                .description(vacancy.getDescription())
                .creationDate(vacancy.getCreationDate())
                .technologies(technologyList)
                .status(vacancy.getStatus())
                .candidateStatistics(vacancyStatusCount)
                .client(clientResponse)
                .users(userResponse)
                .build();
    }
}
