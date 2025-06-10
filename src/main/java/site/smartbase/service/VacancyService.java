package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.VacancyDto;
import site.smartbase.dto.VacancyEditDto;

import java.util.List;

public interface VacancyService {
    @Transactional
    VacancyDto addVacancy(Long currentUserId, VacancyDto vacancyDto);

    List<VacancyDto> getAllVacancies(Long currentUserId);

    List<VacancyDto> getAllVacanciesForUser(Long currentUserId);

    VacancyDto getVacancy(Long vacancyId);

    List<VacancyDto> getVacanciesForCandidate(Long candidateId);

    @Transactional
    VacancyDto changeVacancyStatus(Long vacancyId, String status);

    @Transactional
    VacancyDto addRecruiterToVacancy(Long vacancyId, Long recruiterId);

    @Transactional
    VacancyDto removeRecruiterFromVacancy(Long vacancyId, Long recruiterId);

    @Transactional
    VacancyDto updateVacancy(Long vacancyId, VacancyEditDto vacancyEditDto);

    @Transactional
    void deleteVacancy(Long vacancyId);
}
