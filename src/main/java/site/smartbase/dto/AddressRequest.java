package site.smartbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {
    private String country;

    private String city;

    private String street;

    private String building;

    private String apartment;

    private Long ownerId;

    private String ownableType;
}
