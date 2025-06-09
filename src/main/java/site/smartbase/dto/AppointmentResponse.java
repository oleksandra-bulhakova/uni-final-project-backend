package site.smartbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponse {
    private Long id;
    private String type;
    private String status;
    private OffsetDateTime date;
    private UserResponse user;
    private CandidateResponse candidate;
    private String vacancyName;
}
