package site.smartbase.security;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import site.smartbase.service.RegistrationService;

@TestConfiguration
public class AuthControllerTestConfig {
    @Bean
    public RegistrationService registrationService() {
        return Mockito.mock(RegistrationService.class);
    }
}
