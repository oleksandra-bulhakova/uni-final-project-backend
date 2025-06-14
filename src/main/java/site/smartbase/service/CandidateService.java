package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface CandidateService {
    @Transactional
    CandidateResponse addCandidate(Long currentUserId, CandidateCreationDto candidateCreationDto);

    List<CandidateResponse> getAllCandidates(Long currentUserId);

    CandidateResponse getCandidate(Long candidateId);

    @Transactional
    CandidateResponse updateCandidate(CandidateUpdateDto candidateUpdateDto, Long candidateId);

    @Transactional
    void deleteCandidate(Long candidateId);

    @Transactional
    CandidateResponse addCandidateToVacancy(Long candidateId, Long vacancyId, Long currentUserId);

    @Transactional
    CandidateResponse addCommentToCandidate(Long currentUserId, Long candidateId, CommentRequest commentRequest);

    @Transactional
    void deleteCandidateFromVacancy(Long candidateId, Long vacancyId, Long currentUserId);

    List<CandidateResponse> searchCandidatesByName(String name, Long currentUserId);

    List<CandidateResponse> searchCandidatesByTechnologies(List<Long> technologiesIds, Long currentUserId);

    List<GeneralStatistic> generateReport(Long currentUserId, LocalDateTime start, LocalDateTime end);
}
