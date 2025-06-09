package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.ContactRequest;
import site.smartbase.dto.ContactResponse;
import site.smartbase.dto.ContactUpdate;

public interface ContactService {
    @Transactional
    ContactResponse addContact(Long ownerId, ContactRequest contactRequest);

    @Transactional
    ContactResponse updateContact(Long contactId, ContactUpdate contactUpdate);

    @Transactional
    void deleteContact(Long contactId);
}
