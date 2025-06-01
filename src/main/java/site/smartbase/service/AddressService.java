package site.smartbase.service;

import site.smartbase.dto.AddressRequest;
import site.smartbase.dto.AddressResponse;

public interface AddressService {
    AddressResponse addAddress(AddressRequest addressRequest);
}
