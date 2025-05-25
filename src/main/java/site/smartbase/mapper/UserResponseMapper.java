package site.smartbase.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.AddressResponse;
import site.smartbase.dto.ContactResponse;
import site.smartbase.dto.UserResponse;
import site.smartbase.dto.VacancyListResponse;
import site.smartbase.entity.Address;
import site.smartbase.entity.Contact;
import site.smartbase.entity.User;
import site.smartbase.entity.Vacancy;
import site.smartbase.enums.OwnableType;
import site.smartbase.repository.AddressRepo;
import site.smartbase.repository.ContactRepo;
import site.smartbase.repository.VacancyRepo;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserResponseMapper extends AbstractConverter<User, UserResponse> {
    private final AddressRepo addressRepo;
    private final ContactRepo contactRepo;
    private final VacancyRepo vacancyRepo;

    @Override
    protected UserResponse convert(User user) {
        Address address = addressRepo.findByOwnerIdAndOwnableType(user.getId(), OwnableType.USER)
                .orElse(null);

        AddressResponse addressResponse = null;

        if (address != null) {
            addressResponse = AddressResponse.builder()
                    .country(address.getCountry())
                    .city(address.getCity())
                    .street(address.getStreet())
                    .building(address.getBuilding())
                    .apartment(address.getApartment())
                    .build();
        }

        List<Contact> contacts = contactRepo.findByOwnerIdAndOwnableType(user.getId(), OwnableType.USER);
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

        List<Vacancy> vacancies = vacancyRepo.findByUsers_Id(user.getId());
        List<VacancyListResponse> vacancyListResponses = null;

        if (vacancies != null) {
            vacancyListResponses = vacancies.stream()
                    .map(vacancy -> VacancyListResponse.builder()
                            .id(vacancy.getId())
                            .name(vacancy.getName())
                            .build()).toList();
        }

        return UserResponse.builder()
                .id(user.getId())
                .address(addressResponse)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userRole(user.getRole())
                .imagePath(user.getImagePath())
                .contacts(contactResponses)
                .vacancies(vacancyListResponses)
                .build();
    }
}
