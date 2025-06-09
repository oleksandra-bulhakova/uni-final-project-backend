package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.ClientRequest;
import site.smartbase.dto.ClientResponse;

import java.util.List;

public interface ClientService {
    ClientResponse addClient(Long currentUserId, ClientRequest clientRequest);

    List<ClientResponse> getAllClients(Long currentUserId);

    ClientResponse getClientById(Long clientId);

    @Transactional
    void deleteClient(Long clientId);
}
