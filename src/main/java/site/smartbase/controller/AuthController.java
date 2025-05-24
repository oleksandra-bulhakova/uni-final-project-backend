package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import site.smartbase.annotations.CurrentUserId;
import site.smartbase.dto.CompanyRegistrationRequest;
import site.smartbase.dto.LoginRequest;
import site.smartbase.dto.UserContinueRegistrationRequest;
import site.smartbase.dto.UserRegistrationRequest;
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
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(registrationService.login(request.getEmail(), request.getPassword()));
    }

    @GetMapping("/confirm")
    public RedirectView confirm(@RequestParam("token") String token) {
        registrationService.confirmRegistration(token);
        return new RedirectView("http://localhost:3000/login");
    }

    @PostMapping("/invite")
    public ResponseEntity<String> inviteUser(@CurrentUserId Long currentUserId, @RequestBody UserRegistrationRequest request) {
        registrationService.registerRegularUser(request, currentUserId);
        return ResponseEntity.ok("The invitation was sent");
    }

    @PostMapping("/continue-registration")
    public ResponseEntity<String> continueRegistration(@RequestBody UserContinueRegistrationRequest request) {
        registrationService.continueUserRegistration(request);
        return ResponseEntity.ok("Registered");
    }

    @GetMapping("/finish")
    public RedirectView finish(@RequestParam("token") String token) {
        registrationService.confirmRegistration(token);
        return new RedirectView("http://localhost:3000/finish");
    }

    @GetMapping("/confirm-user-registration")
    public RedirectView confirmUserRegistration(@RequestParam("token") String token) {
        registrationService.confirmRegularUserRegistration(token);
        return new RedirectView("http://localhost:3000/finish");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestParam String email) {
        registrationService.sendResetEmail(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/change-password")
    public RedirectView changePassword(@RequestParam("token") String token) {
        registrationService.confirmRegularUserRegistration(token);
        return new RedirectView("http://localhost:3000/change-password");
    }

    @PostMapping("/set-password")
    public ResponseEntity<Void> setPassword(@RequestBody UserContinueRegistrationRequest request) {
        registrationService.updatePassword(request);
        return ResponseEntity.ok().build();
    }
}
