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
public class UserEditRequest {
    private String firstName;
    private String lastName;
    private List<ContactResponse> contacts;
    private AddressResponse address;
}
