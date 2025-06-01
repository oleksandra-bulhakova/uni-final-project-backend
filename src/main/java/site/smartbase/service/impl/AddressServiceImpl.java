package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import site.smartbase.dto.AddressRequest;
import site.smartbase.dto.AddressResponse;
import site.smartbase.entity.Address;
import site.smartbase.repository.AddressRepo;
import site.smartbase.service.AddressService;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepo addressRepo;
    private final ModelMapper modelMapper;

    @Override
    public AddressResponse addAddress(AddressRequest addressRequest) {
        return modelMapper.map(addressRepo.save(modelMapper.map(addressRequest, Address.class)),AddressResponse.class);
    }
}
