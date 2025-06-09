package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.ContactRequest;
import site.smartbase.dto.ContactResponse;
import site.smartbase.dto.ContactUpdate;
import site.smartbase.entity.Contact;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.ContactRepo;
import site.smartbase.service.ContactService;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepo contactRepo;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ContactResponse addContact(Long ownerId, ContactRequest contactRequest) {
        Contact contact = modelMapper.map(contactRequest, Contact.class);
        contact.setOwnerId(ownerId);
        return modelMapper.map(contactRepo.save(contact), ContactResponse.class);
    }

    @Transactional
    @Override
    public ContactResponse updateContact(Long contactId, ContactUpdate contactUpdate) {
        Contact contact = contactRepo.findById(contactId).orElseThrow(
                () -> new NotFoundException("Contact not found")
        );
        contact.setContact(contactUpdate.getContact());
        return modelMapper.map(contact, ContactResponse.class);
    }

    @Transactional
    @Override
    public void deleteContact(Long contactId) {
        contactRepo.deleteById(contactId);
    }
}
