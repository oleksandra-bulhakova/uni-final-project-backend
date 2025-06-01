package site.smartbase.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.AddressRequest;
import site.smartbase.entity.Address;
import site.smartbase.enums.OwnableType;

@Component
@RequiredArgsConstructor
public class AddressMapper extends AbstractConverter<AddressRequest, Address> {
    @Override
    protected Address convert(AddressRequest addressRequest) {
        OwnableType ownableType = OwnableType.valueOf(addressRequest.getOwnableType());
        return Address.builder()
                .country(addressRequest.getCountry())
                .city(addressRequest.getCity())
                .street(addressRequest.getStreet())
                .building(addressRequest.getBuilding())
                .apartment(addressRequest.getApartment())
                .ownableType(ownableType)
                .ownerId(addressRequest.getOwnerId())
                .build();
    }
}
