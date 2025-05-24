package site.smartbase.service;

import site.smartbase.entity.User;

public interface EmailService {
    void sendConfirmationEmail(Long userId);

    void sendFinishRegistrationEmail(Long userId);

    void sendResetPasswordEmail(User user, String email);
}
