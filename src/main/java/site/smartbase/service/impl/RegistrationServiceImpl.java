package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.CompanyRegistrationRequest;
import site.smartbase.dto.UserContinueRegistrationRequest;
import site.smartbase.dto.UserRegistrationRequest;
import site.smartbase.entity.Company;
import site.smartbase.entity.Contact;
import site.smartbase.entity.User;
import site.smartbase.enums.ContactType;
import site.smartbase.enums.OwnableType;
import site.smartbase.enums.UserRole;
import site.smartbase.exception.*;
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
@Slf4j
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

    @Override
    public String confirmRegistration(String token) {
        User user = userRepo.findByToken(token)
                .orElseThrow(() -> new NotValidTokenException("Invalid token"));

        user.setActive(true);
        user.setToken(null);

        return "Your account has been activated. Now you can login";
    }

    @Async
    @Override
    public void registerRegularUser(UserRegistrationRequest request, Long ownerId) {
        saveRegularUser(request, ownerId);
    }

    private void saveRegularUser(UserRegistrationRequest request, Long ownerId) {
        log.info("ownerId = {}", ownerId);
        Company company = companyRepo.findById(userRepo.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("User not found")).getCompany().getId())
                .orElseThrow(() -> new NotFoundException("Company not found"));

        Contact userEmail = new Contact();
        userEmail.setContact(request.getEmail());
        userEmail.setType(ContactType.MAIN_EMAIL);
        userEmail.setOwnableType(OwnableType.USER);

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRegistrationDate(LocalDate.now());
        user.setCompany(company);
        if (request.getRole().toUpperCase().equals("RECRUITER")) {
            user.setRole(UserRole.RECRUITER);
        } else if (request.getRole().toUpperCase().equals("HIRING_MANAGER")) {
            user.setRole(UserRole.HIRING_MANAGER);
        }

        userRepo.save(user);
        userEmail.setOwnerId(user.getId());
        contactRepo.save(userEmail);

        emailService.sendFinishRegistrationEmail(user.getId());
    }

    @Override
    public void finishRegistration(String token) {
        User user = userRepo.findByToken(token)
                .orElseThrow(() -> new NotValidTokenException("Invalid token"));

        user.setToken(null);
    }

    @Async
    @Override
    public void continueUserRegistration(UserContinueRegistrationRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords don't match");
        }
        emailService.sendConfirmationEmail(setUserPassword(request));
    }

    private Long setUserPassword(UserContinueRegistrationRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords don't match");
        }

        User user = userRepo.findById(contactRepo.findOwnerIdByEmail(request.getEmail(), OwnableType.USER, ContactType.MAIN_EMAIL)
                .orElseThrow(() -> new NotFoundException("Email wasn't found")))
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (user.getActive()) {
            throw new UserAlreadyActivatedException("User is already activated");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return user.getId();
    }

    @Override
    public String confirmRegularUserRegistration(String token) {
        User user = userRepo.findByToken(token)
                .orElseThrow(() -> new NotValidTokenException("Invalid token"));

        user.setToken(null);

        return "You can continue registration";
    }

    @Override
    @Async
    public void sendResetEmail(String email) {
        log.info("email = {}", email);
        User user = userRepo.findById(contactRepo.findOwnerIdByEmail(email, OwnableType.USER, ContactType.MAIN_EMAIL)
                        .orElseThrow(() -> new NotFoundException("Email wasn't found")))
                .orElseThrow(() -> new NotFoundException("User not found"));
        emailService.sendResetPasswordEmail(user, email);
    }

    @Override
    public void updatePassword(UserContinueRegistrationRequest request) {
        User user = userRepo.findById(contactRepo.findOwnerIdByEmail(request.getEmail(), OwnableType.USER, ContactType.MAIN_EMAIL)
                        .orElseThrow(() -> new NotFoundException("Email wasn't found")))
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getPassword()));
    }
}
