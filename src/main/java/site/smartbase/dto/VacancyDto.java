package site.smartbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.smartbase.enums.VacancyStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacancyDto {
    private Long id;
    private String name;
    private LocalDate creationDate;
    private String description;
    private VacancyStatus status;
    private List<UserResponse> users;
    private List<VacancyStatusCount> candidateStatistics;
    private ClientResponse client;
    private List<TechnologyDto> technologies;
}
