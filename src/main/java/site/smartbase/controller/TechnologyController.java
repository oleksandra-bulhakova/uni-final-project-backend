package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.smartbase.dto.TechnologyDto;
import site.smartbase.service.TechnologyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/technologies")
public class TechnologyController {
    private final TechnologyService technologyService;

    @GetMapping
    public ResponseEntity<List<TechnologyDto>> getAllTechnologies() {
        return ResponseEntity.ok(technologyService.getAllTechnologies());
    }

    @PutMapping("/{candidateId}")
    public ResponseEntity<List<TechnologyDto>> updateTechnologyForCandidate(@PathVariable("candidateId") Long candidateId,
                                                                            @RequestBody List<TechnologyDto> technologies) {
        return ResponseEntity.ok(technologyService.addTechnologiesToCandidate(technologies, candidateId));
    }

    @PutMapping("/vacancy/{vacancyId}")
    public ResponseEntity<List<TechnologyDto>> updateTechnologyForVacancy(@PathVariable Long vacancyId, @RequestBody List<TechnologyDto> technologies) {
        return ResponseEntity.ok(technologyService.addTechnologiesToVacancy(technologies, vacancyId));
    }
}
