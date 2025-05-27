package site.smartbase.service;

import site.smartbase.dto.TechnologyDto;

import java.util.List;

public interface TechnologyService {
    TechnologyDto getTechnology(Long technologyId);

    List<TechnologyDto> getAllTechnologies();

    List<TechnologyDto> getAllTechnologiesForVacancy(Long vacancyId);
}
