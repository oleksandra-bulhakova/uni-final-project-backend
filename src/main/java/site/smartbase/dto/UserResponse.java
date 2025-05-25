package site.smartbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.smartbase.enums.UserRole;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private List<ContactResponse> contacts;
    private AddressResponse address;
    private List<VacancyListResponse> vacancies;
    private String imagePath;
}
