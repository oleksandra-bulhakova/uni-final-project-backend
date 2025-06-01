package site.smartbase.mapper;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.AddressResponse;
import site.smartbase.entity.Address;

@Component
public class AddressResponseMapper extends AbstractConverter<Address, AddressResponse> {
    @Override
    protected AddressResponse convert(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .apartment(address.getApartment())
                .build();
    }
}
