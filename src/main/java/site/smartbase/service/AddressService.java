package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.AddressRequest;
import site.smartbase.dto.AddressResponse;
import site.smartbase.dto.UpdateAddressDto;

public interface AddressService {
    AddressResponse addAddress(AddressRequest addressRequest);

    AddressResponse updateAddress(UpdateAddressDto updateAddressDto);

    @Transactional
    void deleteAddress(Long addressId);
}
