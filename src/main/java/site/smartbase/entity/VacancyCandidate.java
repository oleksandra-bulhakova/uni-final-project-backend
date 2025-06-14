package site.smartbase.entity;

import jakarta.persistence.*;
import lombok.*;
import site.smartbase.entity.embeddedId.VacancyCandidateId;

import java.time.LocalDateTime;

@Entity
@Table(name = "vacancy_candidate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class VacancyCandidate {
    @EmbeddedId
    private VacancyCandidateId id = new VacancyCandidateId();

    @ManyToOne
    @MapsId("vacancyId")
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @ManyToOne
    @MapsId("candidateId")
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "added_by")
    private User addedBy;

    private LocalDateTime dateAdded;
}
