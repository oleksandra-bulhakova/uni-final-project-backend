package site.smartbase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AddressRequest {
    private String country;
    private String city;
    private String street;
    private String building;
    private String apartment;
    private Long ownerId;
    private String ownableType;
}
