package site.smartbase.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import site.smartbase.ModelUtils;
import site.smartbase.dto.UserRegistrationRequest;
import site.smartbase.entity.Contact;
import site.smartbase.entity.User;
import site.smartbase.enums.ContactType;
import site.smartbase.enums.OwnableType;
import site.smartbase.enums.UserRole;
import site.smartbase.repository.ContactRepo;
import site.smartbase.repository.UserRepo;
import site.smartbase.service.RegistrationService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@Import(AuthControllerTestConfig.class)
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.liquibase.enabled=false",
        "spring.sql.init.mode=never",
        "AZURE_BLOB=fake-connection-string",
        "spring.mail.username=test@example.com",
        "spring.mail.password=test-password"
})

public class AuthControllerAccessTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ContactRepo contactRepo;

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "OWNER")
    void inviteUserTest() throws Exception {
        User user = new User();
        user.setPassword("12345678");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setRole(UserRole.OWNER);
        userRepo.save(user);

        Contact contact = new Contact();
        contact.setType(ContactType.MAIN_EMAIL);
        contact.setContact("test@gmail.com");
        contact.setOwnableType(OwnableType.USER);
        contact.setOwnerId(user.getId());
        contactRepo.save(contact);

        UserRegistrationRequest userRegistrationRequest = ModelUtils.getUserRegistrationRequest();
        mockMvc.perform(post("/api/auth/invite")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userRegistrationRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "RECRUITER")
    void inviteUser_NoRightsTest() throws Exception {
        User user = new User();
        user.setPassword("12345678");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setRole(UserRole.RECRUITER);
        userRepo.save(user);

        Contact contact = new Contact();
        contact.setType(ContactType.MAIN_EMAIL);
        contact.setContact("test@gmail.com");
        contact.setOwnableType(OwnableType.USER);
        contact.setOwnerId(user.getId());
        contactRepo.save(contact);

        UserRegistrationRequest userRegistrationRequest = ModelUtils.getUserRegistrationRequest();
        mockMvc.perform(post("/api/auth/invite")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userRegistrationRequest)))
                .andExpect(status().isForbidden());
    }
}
