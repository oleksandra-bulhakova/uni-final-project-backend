package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.smartbase.annotations.CurrentUserId;
import site.smartbase.dto.ClientRequest;
import site.smartbase.dto.ClientResponse;
import site.smartbase.service.ClientService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> addClient(@CurrentUserId Long currentUserId, @RequestBody ClientRequest clientRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.addClient(currentUserId, clientRequest));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getClients(@CurrentUserId Long currentUserId) {
        return ResponseEntity.ok(clientService.getAllClients(currentUserId));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(clientService.getClientById(clientId));
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.ok().build();
    }
}
