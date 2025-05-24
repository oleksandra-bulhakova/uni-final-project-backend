package site.smartbase.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.CompanyRegistrationRequest;
import site.smartbase.dto.UserContinueRegistrationRequest;
import site.smartbase.dto.UserRegistrationRequest;

public interface RegistrationService {
    void registerFirst(CompanyRegistrationRequest request);

    String login(String email, String password);

    String confirmRegistration(String token);

    @Async
    void registerRegularUser(UserRegistrationRequest request, Long ownerId);

    void finishRegistration(String token);

    @Async
    void continueUserRegistration(UserContinueRegistrationRequest request);

    @Transactional
    String confirmRegularUserRegistration(String token);

    void sendResetEmail(String email);

    void updatePassword(UserContinueRegistrationRequest request);
}
