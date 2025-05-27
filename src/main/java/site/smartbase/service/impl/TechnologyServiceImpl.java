package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.smartbase.dto.TechnologyDto;
import site.smartbase.entity.Technology;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.TechnologyRepo;
import site.smartbase.service.TechnologyService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologyServiceImpl implements TechnologyService {
    private final TechnologyRepo technologyRepo;

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
}
