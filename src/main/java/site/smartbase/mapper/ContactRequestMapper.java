package site.smartbase.mapper;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.ContactRequest;
import site.smartbase.entity.Contact;
import site.smartbase.enums.ContactType;
import site.smartbase.enums.OwnableType;

@Component
public class ContactRequestMapper extends AbstractConverter<ContactRequest, Contact> {
    @Override
    protected Contact convert(ContactRequest contactRequest) {
        return Contact.builder()
                .ownableType(OwnableType.valueOf(contactRequest.getOwnableType()))
                .contact(contactRequest.getContact())
                .type(ContactType.valueOf(contactRequest.getContactType()))
                .build();
    }
}
