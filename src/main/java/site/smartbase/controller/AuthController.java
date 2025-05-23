package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
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

    @GetMapping("/confirm")
    public RedirectView confirm(@RequestParam("token") String token) {
        registrationService.confirmRegistration(token);
        return new RedirectView("http://localhost:3000/login");
    }
}
