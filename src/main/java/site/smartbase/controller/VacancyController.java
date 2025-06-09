package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.smartbase.annotations.CurrentUserId;
import site.smartbase.dto.VacancyDto;
import site.smartbase.service.VacancyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    @PostMapping
    public ResponseEntity<VacancyDto> addVacancy(@CurrentUserId Long currentUserId,
                                                 @RequestBody VacancyDto vacancyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vacancyService.addVacancy(currentUserId, vacancyDto));
    }

    @GetMapping
    public ResponseEntity<List<VacancyDto>> getAllVacancies(@CurrentUserId Long currentUserId) {
        return ResponseEntity.ok(vacancyService.getAllVacancies(currentUserId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<VacancyDto>> getAllVacanciesForUser(@CurrentUserId Long currentUserId) {
        return ResponseEntity.ok(vacancyService.getAllVacanciesForUser(currentUserId));
    }

    @GetMapping("/{vacancyId}")
    public ResponseEntity<VacancyDto> getVacancyById(@PathVariable Long vacancyId) {
        return ResponseEntity.ok(vacancyService.getVacancy(vacancyId));
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<VacancyDto>> getVacanciesByCandidateId(@PathVariable Long candidateId) {
        return ResponseEntity.ok(vacancyService.getVacanciesForCandidate(candidateId));
    }
}
