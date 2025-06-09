package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.smartbase.dto.ContactRequest;
import site.smartbase.dto.ContactResponse;
import site.smartbase.dto.ContactUpdate;
import site.smartbase.service.ContactService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact")
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/{ownerId}")
    public ResponseEntity<ContactResponse> addContact(@PathVariable("ownerId") Long ownerId, @RequestBody ContactRequest contactRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.addContact(ownerId, contactRequest));
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<ContactResponse> updateContact(@PathVariable("contactId") Long contactId, @RequestBody ContactUpdate contactUpdate) {
        return ResponseEntity.ok(contactService.updateContact(contactId, contactUpdate));
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable("contactId") Long contactId) {
        contactService.deleteContact(contactId);
        return ResponseEntity.ok().build();
    }
}
