package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.CompanyRegistrationRequest;
import site.smartbase.entity.Company;
import site.smartbase.entity.Contact;
import site.smartbase.entity.User;
import site.smartbase.enums.ContactType;
import site.smartbase.enums.OwnableType;
import site.smartbase.enums.UserRole;
import site.smartbase.exception.NotActivatedException;
import site.smartbase.exception.NotFoundException;
import site.smartbase.exception.NotValidToken;
import site.smartbase.exception.WrongPasswordException;
import site.smartbase.repository.CompanyRepo;
import site.smartbase.repository.ContactRepo;
import site.smartbase.repository.UserRepo;
import site.smartbase.service.AuthService;
import site.smartbase.service.EmailService;
import site.smartbase.service.RegistrationService;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepo userRepo;
    private final ContactRepo contactRepo;
    private final CompanyRepo companyRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final EmailService emailService;

    @Override
    @Async
    public void registerFirst(CompanyRegistrationRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords don't match");
        }
        setFirstUserAndCompany(request);
    }

    private void setFirstUserAndCompany(CompanyRegistrationRequest request) {
        Company company = new Company();
        company.setName(request.getCompanyName());
        company.setRegistrationDate(LocalDate.now());
        companyRepo.save(company);

        Contact contactCompanyEmail = new Contact();
        contactCompanyEmail.setContact(request.getEmail());
        contactCompanyEmail.setType(ContactType.EMAIL);
        contactCompanyEmail.setOwnableType(OwnableType.COMPANY);
        contactCompanyEmail.setOwnerId(company.getId());
        contactRepo.save(contactCompanyEmail);

        Contact contactCompanyPhone = new Contact();
        contactCompanyPhone.setContact(request.getPhone());
        contactCompanyPhone.setType(ContactType.PHONE);
        contactCompanyPhone.setOwnableType(OwnableType.COMPANY);
        contactCompanyPhone.setOwnerId(company.getId());
        contactRepo.save(contactCompanyPhone);

        User userLeadOfDepartment = new User();
        userLeadOfDepartment.setFirstName(request.getFirstName());
        userLeadOfDepartment.setLastName(request.getLastName());
        userLeadOfDepartment.setRegistrationDate(LocalDate.now());
        userLeadOfDepartment.setCompany(company);
        userLeadOfDepartment.setRole(UserRole.OWNER);
        userLeadOfDepartment.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(userLeadOfDepartment);

        Contact contactUserEmail = new Contact();
        contactUserEmail.setContact(request.getEmail());
        contactUserEmail.setType(ContactType.MAIN_EMAIL);
        contactUserEmail.setOwnableType(OwnableType.USER);
        contactUserEmail.setOwnerId(userLeadOfDepartment.getId());
        contactRepo.save(contactUserEmail);

        Contact contactUserPhone = new Contact();
        contactUserPhone.setContact(request.getPhone());
        contactUserPhone.setType(ContactType.PHONE);
        contactUserPhone.setOwnableType(OwnableType.USER);
        contactUserPhone.setOwnerId(userLeadOfDepartment.getId());
        contactRepo.save(contactUserPhone);

        emailService.sendConfirmationEmail(userLeadOfDepartment.getId());
    }

    @Override
    public String login(String email, String password) {
        Optional<Contact> emailContact = contactRepo.findByContactAndOwnableTypeAndContactType(email, OwnableType.USER, ContactType.MAIN_EMAIL);
        if (emailContact.isEmpty()) {
            throw new NotFoundException("Contact not found");
        }
        User user = userRepo.findById(emailContact.get().getOwnerId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.getActive()) {
            throw new NotActivatedException("User is not active");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongPasswordException("Invalid credentials");
        }

        return authService.generateToken(email);
    }

    @Transactional
    @Override
    public String confirmRegistration(String token) {
        User user = userRepo.findByToken(token)
                .orElseThrow(() -> new NotValidToken("Invalid token"));

        user.setActive(true);
        user.setToken(null);

        return "Your account has been activated. Now you can login";
    }
}
