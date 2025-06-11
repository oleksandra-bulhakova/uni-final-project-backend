package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.smartbase.annotations.CurrentUserId;
import site.smartbase.dto.VacancyDto;
import site.smartbase.dto.VacancyEditDto;
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

    @PutMapping("/status/{vacancyId}")
    public ResponseEntity<VacancyDto> changeStatus(@PathVariable Long vacancyId, @RequestParam String vacancyStatus) {
        return ResponseEntity.ok(vacancyService.changeVacancyStatus(vacancyId, vacancyStatus));
    }

    @PutMapping("/user/add/{vacancyId}/{userId}")
    public ResponseEntity<VacancyDto> addUserToVacancy(@PathVariable Long vacancyId, @PathVariable Long userId) {
        return ResponseEntity.ok(vacancyService.addRecruiterToVacancy(vacancyId, userId));
    }

    @PutMapping("/user/remove/{vacancyId}/{userId}")
    public ResponseEntity<VacancyDto> removeFromVacancy(@PathVariable Long vacancyId, @PathVariable Long userId) {
        return ResponseEntity.ok(vacancyService.removeRecruiterFromVacancy(vacancyId, userId));
    }

    @PutMapping("/{vacancyId}")
    public ResponseEntity<VacancyDto> updateVacancy(@PathVariable Long vacancyId, @RequestBody VacancyEditDto vacancyEditDto) {
        return ResponseEntity.ok(vacancyService.updateVacancy(vacancyId, vacancyEditDto));
    }

    @DeleteMapping("/{vacancyId}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long vacancyId) {
        vacancyService.deleteVacancy(vacancyId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/client/{vacancyId}/{clientId}")
    public ResponseEntity<VacancyDto> changeClient(@PathVariable Long vacancyId, @PathVariable Long clientId) {
        return ResponseEntity.ok(vacancyService.changeClient(vacancyId, clientId));
    }
}
