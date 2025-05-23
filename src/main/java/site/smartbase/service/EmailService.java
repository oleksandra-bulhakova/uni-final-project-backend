package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;

public interface EmailService {
    @Transactional
    void sendConfirmationEmail(Long userId);
}
