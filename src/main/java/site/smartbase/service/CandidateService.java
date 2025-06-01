package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.CandidateCreationDto;
import site.smartbase.dto.CandidateResponse;

import java.util.List;

public interface CandidateService {
    @Transactional
    CandidateResponse addCandidate(Long currentUserId, CandidateCreationDto candidateCreationDto);

    List<CandidateResponse> getAllCandidates(Long currentUserId);

    CandidateResponse getCandidate(Long candidateId);
}
