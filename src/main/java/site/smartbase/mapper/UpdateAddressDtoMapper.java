package site.smartbase.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.UpdateAddressDto;
import site.smartbase.entity.Address;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.AddressRepo;

@Component
@RequiredArgsConstructor
public class UpdateAddressDtoMapper extends AbstractConverter<UpdateAddressDto, Address> {
    private final AddressRepo addressRepo;

    @Override
    protected Address convert(UpdateAddressDto updateAddressDto) {
        Address address = addressRepo.findById(updateAddressDto.getId()).orElseThrow(() -> new NotFoundException("Address not found"));

        address.setCity(updateAddressDto.getCity());
        address.setCountry(updateAddressDto.getCountry());
        address.setStreet(updateAddressDto.getStreet());
        address.setBuilding(updateAddressDto.getBuilding());
        address.setApartment(updateAddressDto.getApartment());

        return address;
    }
}
