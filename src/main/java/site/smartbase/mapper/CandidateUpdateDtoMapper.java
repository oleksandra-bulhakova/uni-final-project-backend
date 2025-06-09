package site.smartbase.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.CandidateUpdateDto;
import site.smartbase.entity.Candidate;
import site.smartbase.enums.Source;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.CandidateRepo;

@Component
@RequiredArgsConstructor
public class CandidateUpdateDtoMapper extends AbstractConverter<CandidateUpdateDto, Candidate> {
    private final CandidateRepo candidateRepo;

    @Override
    protected Candidate convert(CandidateUpdateDto candidateUpdateDto) {
        Candidate candidate = candidateRepo.findById(candidateUpdateDto.getId()).orElseThrow(() ->
                new NotFoundException("Can't find candidate with id: " + candidateUpdateDto.getId()));

        candidate.setFirstName(candidateUpdateDto.getFirstName());
        candidate.setLastName(candidateUpdateDto.getLastName());
        candidate.setSource(Source.valueOf(candidateUpdateDto.getSource()));
        return candidate;
    }
}
