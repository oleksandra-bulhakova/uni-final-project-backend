package site.smartbase.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.*;
import site.smartbase.entity.*;
import site.smartbase.enums.OwnableType;
import site.smartbase.repository.*;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CandidateResponseMapper extends AbstractConverter<Candidate, CandidateResponse> {
    private final AddressRepo addressRepo;
    private final ContactRepo contactRepo;
    private final VacancyRepo vacancyRepo;
    private final AttachmentRepo attachmentRepo;
    private final CommentRepo commentRepo;

    @Override
    protected CandidateResponse convert(Candidate candidate) {
        List<Address> address = addressRepo.findByOwnerIdAndOwnableType(candidate.getId(), OwnableType.CANDIDATE);

        AddressResponse addressResponse = null;

        if (address != null && !address.isEmpty() && address.getFirst() != null) {
            addressResponse = AddressResponse.builder()
                    .id(address.getFirst().getId())
                    .country(address.getFirst().getCountry())
                    .city(address.getFirst().getCity())
                    .street(address.getFirst().getStreet())
                    .building(address.getFirst().getBuilding())
                    .apartment(address.getFirst().getApartment())
                    .build();
        }

        List<Contact> contacts = contactRepo.findByOwnerIdAndOwnableType(candidate.getId(), OwnableType.CANDIDATE);
        List<ContactResponse> contactResponses = null;

        if (contacts != null) {
            contactResponses = contacts.stream()
                    .map(contact -> ContactResponse.builder()
                            .type(contact.getType())
                            .contact(contact.getContact())
                            .id(contact.getId())
                            .build())
                    .toList();
        }

        List<Vacancy> vacancies = vacancyRepo.findByCandidates_Id(candidate.getId());
        List<VacancyListResponse> vacancyListResponses = null;

        if (vacancies != null) {
            vacancyListResponses = vacancies.stream()
                    .map(vacancy -> VacancyListResponse.builder()
                            .id(vacancy.getId())
                            .name(vacancy.getName())
                            .build()).toList();
        }

        List<Attachment> attachments = attachmentRepo.findAllByCandidate_Id(candidate.getId());
        List<AttachmentDto> attachmentDtos = null;

        if (attachments != null) {
            attachmentDtos = attachments.stream()
                    .map(attachment -> AttachmentDto.builder()
                            .id(attachment.getId())
                            .attachmentPath(attachment.getAttachmentPath())
                            .build()).toList();
        }

        List<Comment> comments = commentRepo.findByAddressee_Id(candidate.getId());
        List<CommentResponse> commentResponses = null;
        if (comments != null) {
            commentResponses = comments.stream()
                    .sorted(Comparator.comparing(Comment::getDate).reversed())
                    .map(comment -> CommentResponse.builder()
                    .id(comment.getId())
                    .date(comment.getDate())
                    .author(UserResponse.builder()
                            .id(comment.getAuthor().getId())
                            .imagePath(comment.getAuthor().getImagePath())
                            .firstName(comment.getAuthor().getFirstName())
                            .lastName(comment.getAuthor().getLastName())
                            .build())
                    .description(comment.getDescription())
                    .addressee(CandidateResponse.builder()
                            .id(comment.getAddressee().getId())
                            .firstName(comment.getAddressee().getFirstName())
                            .id(comment.getAddressee().getId())
                            .build())
                    .build()).toList();
        }

        List<Technology> technologies = candidate.getTechnologies();
        List<TechnologyDto> technologyDtos = null;
        if (technologies != null) {
            technologyDtos = technologies.stream().map(technology ->
                    TechnologyDto.builder()
                            .id(technology.getId())
                            .name(technology.getName())
                            .build()).toList();
        }

        return CandidateResponse.builder().
                id(candidate.getId())
                .firstName(candidate.getFirstName())
                .lastName(candidate.getLastName())
                .source(candidate.getSource().toString())
                .contacts(contactResponses)
                .registrationDate(candidate.getRegistrationDate())
                .attachments(attachmentDtos)
                .vacancies(vacancyListResponses)
                .comments(commentResponses)
                .technologies(technologyDtos)
                .address(addressResponse).build();
    }
}
