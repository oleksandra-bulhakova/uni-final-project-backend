package site.smartbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.smartbase.enums.ContactType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactResponse {
    private Long id;

    private ContactType type;

    private String contact;
}
