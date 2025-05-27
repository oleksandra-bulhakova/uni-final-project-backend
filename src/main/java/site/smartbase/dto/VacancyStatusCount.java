package site.smartbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.smartbase.enums.AppointmentStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacancyStatusCount {
    private String name;
    private AppointmentStatus status;
    private Long count;
}
