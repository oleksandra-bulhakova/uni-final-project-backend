package site.smartbase.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.AddressResponse;
import site.smartbase.dto.ClientResponse;
import site.smartbase.dto.ContactResponse;
import site.smartbase.dto.VacancyListResponse;
import site.smartbase.entity.Address;
import site.smartbase.entity.Client;
import site.smartbase.entity.Contact;
import site.smartbase.entity.Vacancy;
import site.smartbase.enums.OwnableType;
import site.smartbase.repository.AddressRepo;
import site.smartbase.repository.ContactRepo;
import site.smartbase.repository.VacancyRepo;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientResponseMapper extends AbstractConverter<Client, ClientResponse> {
    private final AddressRepo addressRepo;
    private final ContactRepo contactRepo;
    private final VacancyRepo vacancyRepo;

    @Override
    protected ClientResponse convert(Client client) {
        List<Address> address = addressRepo.findByOwnerIdAndOwnableType(client.getId(), OwnableType.CLIENT);

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

        List<Contact> contacts = contactRepo.findByOwnerIdAndOwnableType(client.getId(), OwnableType.CLIENT);
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

        List<Vacancy> vacancies = vacancyRepo.findByClient_Id(client.getId());
        List<VacancyListResponse> vacancyListResponses = null;

        if (vacancies != null) {
            vacancyListResponses = vacancies.stream()
                    .map(vacancy -> VacancyListResponse.builder()
                            .id(vacancy.getId())
                            .name(vacancy.getName())
                            .build()).toList();
        }

        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .registrationDate(client.getRegistrationDate())
                .vacancies(vacancyListResponses)
                .address(addressResponse)
                .contacts(contactResponses)
                .build();
    }
}
