package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.CandidateCreationDto;
import site.smartbase.dto.CandidateResponse;
import site.smartbase.entity.*;
import site.smartbase.enums.ContactType;
import site.smartbase.enums.OwnableType;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.CandidateRepo;
import site.smartbase.repository.CompanyRepo;
import site.smartbase.repository.ContactRepo;
import site.smartbase.repository.UserRepo;
import site.smartbase.service.CandidateService;

import java.time.LocalDate;
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
}
