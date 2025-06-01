package site.smartbase.mapper;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.CandidateCreationDto;
import site.smartbase.entity.Candidate;
import site.smartbase.enums.Source;

import java.time.LocalDate;

@Component
public class CandidateMapper extends AbstractConverter<CandidateCreationDto, Candidate> {
    @Override
    protected Candidate convert(CandidateCreationDto candidateCreationDto) {
        if (candidateCreationDto == null) {
            return null;
        }

        return Candidate.builder()
                .firstName(candidateCreationDto.getFirstName())
                .lastName(candidateCreationDto.getLastName())
                .registrationDate(LocalDate.now())
                .source(Source.valueOf(candidateCreationDto.getSource()))
                .build();
    }
}
