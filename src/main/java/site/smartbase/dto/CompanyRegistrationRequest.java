package site.smartbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanyRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String companyName;
    private String password;
    private String confirmPassword;
}
