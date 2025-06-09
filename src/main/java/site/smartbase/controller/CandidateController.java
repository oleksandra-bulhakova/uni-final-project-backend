package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.smartbase.annotations.CurrentUserId;
import site.smartbase.dto.*;
import site.smartbase.service.AppointmentService;
import site.smartbase.service.CandidateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/candidates")
public class CandidateController {
    private final CandidateService candidateService;
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<CandidateResponse> createCandidate(@CurrentUserId Long currentUserId,
                                                             @RequestBody CandidateCreationDto candidateCreationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(candidateService.addCandidate(currentUserId, candidateCreationDto));
    }

    @GetMapping
    public ResponseEntity<List<CandidateResponse>> getCandidates(@CurrentUserId Long currentUserId) {
        return ResponseEntity.ok().body(candidateService.getAllCandidates(currentUserId));
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<CandidateResponse> getCandidate(@PathVariable Long candidateId) {
        return ResponseEntity.ok().body(candidateService.getCandidate(candidateId));
    }

    @PutMapping("/{candidateId}")
    public ResponseEntity<CandidateResponse> updateCandidate(@PathVariable(name = "candidateId") Long candidateId, @RequestBody CandidateUpdateDto candidateUpdateDto) {
        return ResponseEntity.ok().body(candidateService.updateCandidate(candidateUpdateDto, candidateId));
    }

    @DeleteMapping("/{candidateId}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long candidateId) {
        candidateService.deleteCandidate(candidateId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{candidateId}/{vacancyId}")
    public ResponseEntity<CandidateResponse> addCandidateToVacancy(@PathVariable Long candidateId, @PathVariable Long vacancyId, @CurrentUserId Long currentUserId) {
        return ResponseEntity.ok().body(candidateService.addCandidateToVacancy(candidateId, vacancyId, currentUserId));
    }

    @PutMapping("/comment/{candidateId}")
    public ResponseEntity<CandidateResponse> addCommentToCandidate(@PathVariable Long candidateId,
                                                                   @CurrentUserId Long currentUserId, @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok().body(candidateService.addCommentToCandidate(currentUserId, candidateId, commentRequest));
    }

    @PutMapping("/appointment/{candidateId}/{vacancyId}")
    public ResponseEntity<AppointmentResponse> addAppointmentToCandidate(@PathVariable Long candidateId, @PathVariable Long vacancyId,
                                                                         @CurrentUserId Long currentUserId, @RequestBody AppointmentRequest appointmentRequest) {
        return ResponseEntity.ok().body(appointmentService.addAppointment(appointmentRequest, currentUserId, candidateId, vacancyId));
    }

    @GetMapping("/appointment/{candidateId}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsForCandidate(@PathVariable Long candidateId) {
        return ResponseEntity.ok(appointmentService.getAllAppointmentsForCandidate(candidateId));
    }
}
