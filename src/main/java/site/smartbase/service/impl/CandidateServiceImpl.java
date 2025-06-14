package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.*;
import site.smartbase.entity.*;
import site.smartbase.entity.embeddedId.VacancyCandidateId;
import site.smartbase.enums.AppointmentType;
import site.smartbase.enums.ContactType;
import site.smartbase.enums.OwnableType;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.*;
import site.smartbase.service.CandidateService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepo candidateRepo;
    private final ContactRepo contactRepo;
    private final ModelMapper modelMapper;
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;
    private final VacancyRepo vacancyRepo;
    private final CommentRepo commentRepo;
    private final VacancyCandidateRepo vacancyCandidateRepo;
    private final AppointmentRepo appointmentRepo;

    @Transactional
    @Override
    public CandidateResponse addCandidate(Long currentUserId, CandidateCreationDto candidateCreationDto) {
        List<Contact> contacts = createFirstContactList(candidateCreationDto.getEmail(), candidateCreationDto.getPhone(),
                candidateCreationDto.getLink());

        User user = userRepo.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Company company = companyRepo.findById(user.getCompany().getId())
                .orElseThrow(() -> new NotFoundException("Company not found"));

        Candidate candidate = modelMapper.map(candidateCreationDto, Candidate.class);
        candidate.setCompany(company);
        candidateRepo.save(candidate);

        contacts.forEach(contact -> contact.setOwnerId(candidate.getId()));
        contactRepo.saveAll(contacts);

        if (candidate.getComments() == null) {
            List<Comment> comments = new ArrayList<>();
            Comment comment = new Comment();
            comment.setAddressee(candidate);
            comment.setAuthor(user);
            comment.setDate(LocalDate.now());
            comment.setDescription("Кандидата додано до SmartBase");
            comments.add(comment);
            candidate.setComments(comments);
        }

        return modelMapper.map(candidate, CandidateResponse.class);
    }

    private List<Contact> createFirstContactList(String candidateEmail, String candidatePhone,
                                                 String candidateLink) {
        List<Contact> contactList = new ArrayList<>();
        Contact email;
        Contact phone;
        Contact link;

        if (candidateEmail != null && !candidateEmail.isEmpty()) {
            email = new Contact();
            email.setType(ContactType.EMAIL);
            email.setContact(candidateEmail);
            email.setOwnableType(OwnableType.CANDIDATE);
            contactList.add(email);
        }

        if (candidatePhone != null && !candidatePhone.isEmpty()) {
            phone = new Contact();
            phone.setType(ContactType.PHONE);
            phone.setContact(candidatePhone);
            phone.setOwnableType(OwnableType.CANDIDATE);
            contactList.add(phone);
        }

        if (candidateLink != null && !candidateLink.isEmpty()) {
            link = new Contact();
            link.setType(ContactType.LINK);
            link.setContact(candidateLink);
            link.setOwnableType(OwnableType.CANDIDATE);
            contactList.add(link);
        }
        return contactList;
    }

    @Override
    public List<CandidateResponse> getAllCandidates(Long currentUserId) {
        Company company = companyRepo.findById(userRepo.findById(currentUserId)
                        .orElseThrow(() -> new NotFoundException("User not found")).getCompany().getId())
                .orElseThrow(() -> new NotFoundException("Company not found"));

        List<Candidate> candidates = candidateRepo.findByCompany_Id(company.getId());
        return candidates.stream().map(candidate -> modelMapper.map(candidate, CandidateResponse.class)).toList();
    }

    @Override
    public CandidateResponse getCandidate(Long candidateId) {
        return modelMapper.map(candidateRepo.findById(candidateId), CandidateResponse.class);
    }

    @Transactional
    @Override
    public CandidateResponse updateCandidate(CandidateUpdateDto candidateUpdateDto, Long candidateId) {
        candidateUpdateDto.setId(candidateId);
        return modelMapper.map(candidateRepo.save(modelMapper.map(candidateUpdateDto, Candidate.class)), CandidateResponse.class);
    }

    @Transactional
    @Override
    public void deleteCandidate(Long candidateId) {
        candidateRepo.deleteById(candidateId);
    }

    @Transactional
    @Override
    public CandidateResponse addCandidateToVacancy(Long candidateId, Long vacancyId, Long currentUserId) {
        Vacancy vacancy = vacancyRepo.findById(vacancyId).orElseThrow(() -> new NotFoundException("Vacancy not found with id: " + vacancyId));
        Candidate candidate = candidateRepo.findById(candidateId).orElseThrow(() -> new NotFoundException("Candidate not found"));
        User user = userRepo.findById(currentUserId).orElseThrow(() -> new NotFoundException("User not found"));

        VacancyCandidate vacancyCandidate = new VacancyCandidate();

        VacancyCandidateId id = new VacancyCandidateId();
        id.setCandidateId(candidateId);
        id.setVacancyId(vacancyId);
        vacancyCandidate.setId(id);

        vacancyCandidate.setCandidate(candidate);
        vacancyCandidate.setVacancy(vacancy);
        vacancyCandidate.setAddedBy(user);
        vacancyCandidate.setDateAdded(LocalDateTime.now());

        vacancyCandidateRepo.save(vacancyCandidate);

        Comment comment = new Comment();
        comment.setDate(LocalDate.now());
        comment.setAddressee(candidate);
        comment.setAuthor(user);
        comment.setDescription("Кандидата додано до вакансії " + vacancy.getName());
        commentRepo.save(comment);

        return modelMapper.map(candidate, CandidateResponse.class);
    }

    @Transactional
    @Override
    public CandidateResponse addCommentToCandidate(Long currentUserId, Long candidateId, CommentRequest commentRequest) {
        Candidate candidate = candidateRepo.findById(candidateId).orElseThrow(() -> new NotFoundException("Candidate not found"));
        User user = userRepo.findById(currentUserId).orElseThrow(() -> new NotFoundException("User not found"));

        Comment comment = new Comment();
        comment.setDate(LocalDate.now());
        comment.setAddressee(candidate);
        comment.setAuthor(user);
        comment.setDescription(commentRequest.getDescription());
        commentRepo.save(comment);

        candidate.getComments().add(comment);

        return modelMapper.map(comment, CandidateResponse.class);
    }

    @Transactional
    @Override
    public void deleteCandidateFromVacancy(Long candidateId, Long vacancyId, Long currentUserId) {
        Vacancy vacancy = vacancyRepo.findById(vacancyId).orElseThrow(() -> new NotFoundException("Vacancy not found with id: " + vacancyId));
        Candidate candidate = candidateRepo.findById(candidateId).orElseThrow(() -> new NotFoundException("Candidate not found"));
        User user = userRepo.findById(currentUserId).orElseThrow(() -> new NotFoundException("User not found"));

        VacancyCandidateId id = new VacancyCandidateId();
        id.setCandidateId(candidateId);
        id.setVacancyId(vacancyId);

        vacancyCandidateRepo.deleteById(id);

        Comment comment = new Comment();
        comment.setDate(LocalDate.now());
        comment.setAddressee(candidate);
        comment.setAuthor(user);
        comment.setDescription("Кандидата видалено з вакансії " + vacancy.getName());
        commentRepo.save(comment);
    }

    @Override
    public List<CandidateResponse> searchCandidatesByName(String name, Long currentUserId) {
        User user = userRepo.findById(currentUserId).orElseThrow(() -> new NotFoundException("User not found"));
        Company company = companyRepo.findById(user.getCompany().getId()).orElseThrow(() -> new NotFoundException("Company not found"));

        List<Candidate> candidates = candidateRepo.searchCandidateByName(name, company.getId());

        return candidates.stream().map(candidate -> modelMapper.map(candidate, CandidateResponse.class)).toList();
    }

    @Override
    public List<CandidateResponse> searchCandidatesByTechnologies(List<Long> technologiesIds, Long currentUserId) {
        User user = userRepo.findById(currentUserId).orElseThrow(() -> new NotFoundException("User not found"));
        Company company = companyRepo.findById(user.getCompany().getId()).orElseThrow(() -> new NotFoundException("Company not found"));

        List<Candidate> candidates = candidateRepo.searchCandidateByTechnologies(company.getId(), technologiesIds, (long) technologiesIds.size());

        return candidates.stream().map(candidate -> modelMapper.map(candidate, CandidateResponse.class)).toList();
    }

    @Override
    public List<GeneralStatistic> generateReport(Long currentUserId, LocalDateTime start, LocalDateTime end) {
        User currentUser = userRepo.findById(currentUserId).orElseThrow(() -> new NotFoundException("User not found"));
        List<User> users = userRepo.findByCompany_id(currentUser.getCompany().getId());

        List<Long> userIds = users.stream().map(User::getId).toList();

        List<GeneralStatistic> statistics = vacancyCandidateRepo.countByUsersBetweenDates(userIds, start, end);

        OffsetDateTime startOffset = start.atOffset(ZoneOffset.UTC);
        OffsetDateTime endOffset = end.atOffset(ZoneOffset.UTC);

        for (GeneralStatistic stats : statistics) {
            stats.setPreScreens(appointmentRepo.countByTypeAndDateForHost(stats.getUserId(), startOffset, endOffset, AppointmentType.PRESCREEN));
            stats.setEnglishCheck(appointmentRepo.countByTypeAndDateForHost(stats.getUserId(), startOffset, endOffset, AppointmentType.ENGLISH_CHECK));
            stats.setInterviews(appointmentRepo.countByTypeAndDateForHost(stats.getUserId(), startOffset, endOffset, AppointmentType.INTERVIEW));
            stats.setOffers(appointmentRepo.countByTypeAndDateForHost(stats.getUserId(), startOffset, endOffset, AppointmentType.OFFER));
            stats.setHires(appointmentRepo.countByTypeAndDateForHost(stats.getUserId(), startOffset, endOffset, AppointmentType.HIRING));
        }

        return statistics;
    }
}
