package site.smartbase.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.entity.Contact;
import site.smartbase.entity.User;
import site.smartbase.enums.ContactType;
import site.smartbase.enums.OwnableType;
import site.smartbase.exception.EmailException;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.ContactRepo;
import site.smartbase.repository.UserRepo;
import site.smartbase.service.EmailService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final UserRepo userRepo;
    private final ContactRepo contactRepo;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private void send(String to, String subject, String htmlText) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlText, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailException("Error during sending the email");
        }
    }

    @Transactional
    @Override
    public void sendConfirmationEmail(Long userId) {
        String token = UUID.randomUUID().toString();
        User user = userRepo.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        user.setToken(token);
        String confirmLink = "http://localhost:8081/api/auth/confirm?token=" + token;

        String message = """
        <!DOCTYPE html>
        <html lang="uk">
        <head>
          <meta charset="UTF-8">
          <title>Підтвердження реєстрації</title>
        </head>
        <body style="margin:0;padding:0;font-family:Arial,sans-serif;background-color:#f6f6f6;">
          <table align="center" cellpadding="0" cellspacing="0" width="100%%" style="max-width: 600px; background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 12px rgba(0,0,0,0.1);">
            <tr>
              <td style="background-color: #fcb03d; padding: 24px; text-align: center;">
                <h1 style="color: white; margin: 0;">SmartBase</h1>
              </td>
            </tr>
            <tr>
              <td style="padding: 24px; color: #2e2e3a;">
                <p style="font-size: 16px;">Привіт!</p>
                <p style="font-size: 16px;">
                  Дякуємо за реєстрацію в <strong>SmartBase</strong>. Щоб завершити створення облікового запису, натисніть кнопку нижче:
                </p>
                <div style="text-align: center; margin: 32px 0;">
                  <a href="%s" style="background-color: #fcb03d; color: white; padding: 12px 24px; border-radius: 8px; text-decoration: none; font-weight: bold;">Підтвердити реєстрацію</a>
                </div>
                <p style="font-size: 16px; color: #555;">
                  Якщо ви не реєструвались, просто проігноруйте цей лист.
                </p>
              </td>
            </tr>
            <tr>
              <td style="background-color: #f6f6f6; text-align: center; padding: 16px; font-size: 12px; color: #999;">
                © 2025 SmartBase. Усі права захищено.
              </td>
            </tr>
          </table>
        </body>
        </html>
        """.formatted(confirmLink);

        Contact contact = contactRepo.findByOwnerIdAndOwnableTypeAndContactType(userId, OwnableType.USER, ContactType.MAIN_EMAIL)
                        .orElseThrow(() -> new NotFoundException("Contact not found"));

        send(contact.getContact(), "Підтвердження реєстрації", message);
    }
}
