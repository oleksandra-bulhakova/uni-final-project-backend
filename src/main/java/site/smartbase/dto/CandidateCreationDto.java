package site.smartbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateCreationDto {
    private String firstName;
    private String lastName;
    private String source;
    private LocalDate registrationDate;
    private String email;
    private String phone;
    private String link;
}
