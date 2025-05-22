package site.smartbase.service;

import site.smartbase.dto.CompanyRegistrationRequest;

public interface RegistrationService {
    void registerFirst(CompanyRegistrationRequest request);

    String login(String email, String password);
}
