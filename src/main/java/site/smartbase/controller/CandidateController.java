package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.smartbase.annotations.CurrentUserId;
import site.smartbase.dto.CandidateCreationDto;
import site.smartbase.dto.CandidateResponse;
import site.smartbase.service.CandidateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/candidates")
public class CandidateController {
    private final CandidateService candidateService;

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
}
