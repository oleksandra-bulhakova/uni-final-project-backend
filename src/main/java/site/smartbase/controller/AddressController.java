package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.smartbase.dto.AddressRequest;
import site.smartbase.dto.AddressResponse;
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
}
