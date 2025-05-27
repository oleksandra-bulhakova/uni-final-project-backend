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
public class ClientResponse {
    private Long id;
    private String name;
    private LocalDate registrationDate;
    private List<VacancyListResponse> vacancies;
    private List<ContactResponse> contacts;
    private AddressResponse address;
}
