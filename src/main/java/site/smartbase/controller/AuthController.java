package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.smartbase.dto.CompanyRegistrationRequest;
import site.smartbase.dto.LoginRequest;
import site.smartbase.service.RegistrationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerFirst(@RequestBody CompanyRegistrationRequest request) {
        registrationService.registerFirst(request);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(registrationService.login(request.getEmail(), request.getPassword()));
    }
}
