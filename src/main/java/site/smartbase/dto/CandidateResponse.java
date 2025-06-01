package site.smartbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String source;
    private LocalDate registrationDate;
    private List<VacancyListResponse> vacancies;
    private List<ContactResponse> contacts;
    private AddressResponse address;
    private List<AttachmentDto> attachments;
    private List<CommentResponse> comments;
    private List<TechnologyDto> technologies;
}
