package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.smartbase.dto.AddressRequest;
import site.smartbase.dto.AddressResponse;
import site.smartbase.dto.UpdateAddressDto;
import site.smartbase.service.AddressService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponse> addAddress(@RequestBody AddressRequest addressRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.addAddress(addressRequest));
    }

    @PutMapping
    public ResponseEntity<AddressResponse> updateAddress(@RequestBody UpdateAddressDto updateAddressDto) {
        return ResponseEntity.ok(addressService.updateAddress(updateAddressDto));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok().build();
    }
}
