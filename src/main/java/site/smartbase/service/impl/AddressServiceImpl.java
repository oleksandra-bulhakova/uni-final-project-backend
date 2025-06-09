package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.AddressRequest;
import site.smartbase.dto.AddressResponse;
import site.smartbase.dto.UpdateAddressDto;
import site.smartbase.entity.Address;
import site.smartbase.repository.AddressRepo;
import site.smartbase.service.AddressService;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepo addressRepo;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public AddressResponse addAddress(AddressRequest addressRequest) {
        return modelMapper.map(addressRepo.save(modelMapper.map(addressRequest, Address.class)),AddressResponse.class);
    }

    @Override
    @Transactional
    public AddressResponse updateAddress(UpdateAddressDto updateAddressDto) {
        return modelMapper.map(addressRepo.save(modelMapper.map(updateAddressDto, Address.class)),AddressResponse.class);
    }

    @Transactional
    @Override
    public void deleteAddress(Long addressId) {
        addressRepo.deleteById(addressId);
    }
}
