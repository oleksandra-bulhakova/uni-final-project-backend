package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.TechnologyDto;

import java.util.List;

public interface TechnologyService {
    TechnologyDto getTechnology(Long technologyId);

    List<TechnologyDto> getAllTechnologies();

    List<TechnologyDto> getAllTechnologiesForVacancy(Long vacancyId);

    List<TechnologyDto> addTechnologiesToCandidate(List<TechnologyDto> technologies, Long candidateId);

    @Transactional
    List<TechnologyDto> addTechnologiesToVacancy(List<TechnologyDto> technologies, Long vacancyId);
}
