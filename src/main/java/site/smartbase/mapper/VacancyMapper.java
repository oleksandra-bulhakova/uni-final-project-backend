package site.smartbase.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.VacancyDto;
import site.smartbase.entity.Technology;
import site.smartbase.entity.Vacancy;
import site.smartbase.enums.VacancyStatus;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VacancyMapper extends AbstractConverter<VacancyDto, Vacancy> {

    @Override
    protected Vacancy convert(VacancyDto vacancyDto) {
        List<Technology> technologies = vacancyDto.getTechnologies().stream()
                .map(technologyDto -> Technology.builder()
                        .id(technologyDto.getId())
                        .name(technologyDto.getName())
                        .build()).toList();

        return Vacancy.builder()
                .name(vacancyDto.getName())
                .description(vacancyDto.getDescription())
                .creationDate(LocalDate.now())
                .technologies(technologies)
                .status(VacancyStatus.IN_PROGRESS)
                .build();
    }
}
