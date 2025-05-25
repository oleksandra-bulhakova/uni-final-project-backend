package site.smartbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private Long id;

    private String country;

    private String city;

    private String street;

    private String building;

    private String apartment;
}
