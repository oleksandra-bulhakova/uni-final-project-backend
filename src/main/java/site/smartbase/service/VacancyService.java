package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    @Transactional
    VacancyDto addVacancy(Long currentUserId, VacancyDto vacancyDto);

    List<VacancyDto> getAllVacancies(Long currentUserId);

    List<VacancyDto> getAllVacanciesForUser(Long currentUserId);

    VacancyDto getVacancy(Long vacancyId);
}
