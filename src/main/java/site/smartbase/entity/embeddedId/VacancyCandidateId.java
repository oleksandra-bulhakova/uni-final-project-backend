package site.smartbase.entity.embeddedId;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VacancyCandidateId implements Serializable {
    private Long vacancyId;
    private Long candidateId;
}
