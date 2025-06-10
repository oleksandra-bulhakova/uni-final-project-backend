package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.TechnologyDto;
import site.smartbase.entity.Candidate;
import site.smartbase.entity.Technology;
import site.smartbase.entity.Vacancy;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.CandidateRepo;
import site.smartbase.repository.TechnologyRepo;
import site.smartbase.repository.VacancyRepo;
import site.smartbase.service.TechnologyService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologyServiceImpl implements TechnologyService {
    private final TechnologyRepo technologyRepo;
    private final CandidateRepo candidateRepo;
    private final VacancyRepo vacancyRepo;

    @Override
    public TechnologyDto getTechnology(Long technologyId) {
        Technology technology = technologyRepo.findById(technologyId).orElseThrow(() -> new NotFoundException("Technology not found"));

        return TechnologyDto.builder()
                .id(technology.getId())
                .name(technology.getName())
                .build();
    }

    @Override
    public List<TechnologyDto> getAllTechnologies() {
        List<Technology> technologies = technologyRepo.findAll();
        List<TechnologyDto> technologiesDto = new ArrayList<>();
        if (!technologies.isEmpty()) {
            technologiesDto = technologies.stream()
                    .map(technology -> TechnologyDto.builder()
                            .id(technology.getId())
                            .name(technology.getName())
                            .build()).toList();
        }
        return technologiesDto;
    }

    @Override
    public List<TechnologyDto> getAllTechnologiesForVacancy(Long vacancyId) {
        List<Technology> technologies = technologyRepo.findByVacancies_Id(vacancyId);
        List<TechnologyDto> technologiesDto = new ArrayList<>();
        if (!technologies.isEmpty()) {
            technologiesDto = technologies.stream()
                    .map(technology -> TechnologyDto.builder()
                            .id(technology.getId())
                            .name(technology.getName())
                            .build()).toList();
        }
        return technologiesDto;
    }

    @Override
    @Transactional
    public List<TechnologyDto> addTechnologiesToCandidate(List<TechnologyDto> technologies, Long candidateId) {
        Candidate candidate = candidateRepo.findById(candidateId).orElseThrow(() -> new NotFoundException("Candidate not found"));

        List<Long> incomingIds = technologies.stream()
                .map(TechnologyDto::getId)
                .toList();

        candidate.getTechnologies().removeIf(tech -> !incomingIds.contains(tech.getId()));

        for (TechnologyDto dto : technologies) {
            boolean alreadyPresent = candidate.getTechnologies().stream()
                    .anyMatch(existing -> existing.getId().equals(dto.getId()));
            if (!alreadyPresent) {
                Technology tech = Technology.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .build();
                candidate.getTechnologies().add(tech);
            }
        }

        return candidate.getTechnologies().stream().map(
                technology -> TechnologyDto.builder()
                        .name(technology.getName())
                        .id(technology.getId())
                        .build()).toList();
    }

    @Transactional
    @Override
    public List<TechnologyDto> addTechnologiesToVacancy(List<TechnologyDto> technologies, Long vacancyId) {
        Vacancy vacancy = vacancyRepo.findById(vacancyId).orElseThrow(() -> new NotFoundException("Vacancy not found"));

        List<Long> incomingIds = technologies.stream()
                .map(TechnologyDto::getId)
                .toList();

        vacancy.getTechnologies().removeIf(tech -> !incomingIds.contains(tech.getId()));

        for (TechnologyDto dto : technologies) {
            boolean alreadyPresent = vacancy.getTechnologies().stream()
                    .anyMatch(existing -> existing.getId().equals(dto.getId()));
            if (!alreadyPresent) {
                Technology tech = Technology.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .build();
                vacancy.getTechnologies().add(tech);
            }
        }

        return vacancy.getTechnologies().stream().map(
                technology -> TechnologyDto.builder()
                        .name(technology.getName())
                        .id(technology.getId())
                        .build()).toList();
    }
}
