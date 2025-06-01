package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.ClientRequest;
import site.smartbase.dto.ClientResponse;
import site.smartbase.entity.Client;
import site.smartbase.entity.Company;
import site.smartbase.entity.Contact;
import site.smartbase.enums.ContactType;
import site.smartbase.enums.OwnableType;
import site.smartbase.exception.AlreadyExists;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.ClientRepo;
import site.smartbase.repository.CompanyRepo;
import site.smartbase.repository.ContactRepo;
import site.smartbase.repository.UserRepo;
import site.smartbase.service.ClientService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepo clientRepo;
    private final ContactRepo contactRepo;
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ClientResponse addClient(Long currentUserId, ClientRequest clientRequest) {
        if (contactRepo.findByContactAndOwnableTypeAndContactType(clientRequest.getEmail(), OwnableType.CLIENT, ContactType.MAIN_EMAIL).isPresent()) {
            throw new AlreadyExists("Client already exists");
        }

        Company company = companyRepo.findById(userRepo.findById(currentUserId)
                        .orElseThrow(() -> new NotFoundException("User not found")).getCompany().getId())
                .orElseThrow(() -> new NotFoundException("Company not found"));

        Client client = new Client();
        client.setName(clientRequest.getName());
        client.setRegistrationDate(LocalDate.now());
        client.setCompany(company);
        clientRepo.save(client);

        Contact contact = new Contact();
        contact.setContact(clientRequest.getEmail());
        contact.setOwnableType(OwnableType.CLIENT);
        contact.setType(ContactType.MAIN_EMAIL);
        contact.setOwnerId(client.getId());
        contactRepo.save(contact);

        return modelMapper.map(client, ClientResponse.class);
    }

    @Override
    public List<ClientResponse> getAllClients(Long currentUserId) {
        Company company = companyRepo.findById(userRepo.findById(currentUserId)
                        .orElseThrow(() -> new NotFoundException("User not found")).getCompany().getId())
                .orElseThrow(() -> new NotFoundException("Company not found"));

        List<Client> clients = clientRepo.findAllByCompany_Id(company.getId());
        List<ClientResponse> clientResponses = null;
        if (clients != null) {
            clientResponses = clients.stream()
                    .map(client -> modelMapper.map(client, ClientResponse.class)).toList();
        }
        return clientResponses;
    }

    @Override
    public ClientResponse getClientById(Long clientId) {
        return modelMapper.map(clientRepo.findById(clientId), ClientResponse.class);
    }
}
