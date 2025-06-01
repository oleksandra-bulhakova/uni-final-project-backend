package site.smartbase.mapper;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.AttachmentDto;
import site.smartbase.entity.Attachment;

@Component
public class AttachmentDtoMapper extends AbstractConverter<Attachment, AttachmentDto> {
    @Override
    protected AttachmentDto convert(Attachment attachment) {
        return AttachmentDto.builder()
                .id(attachment.getId())
                .attachmentPath(attachment.getAttachmentPath())
                .build();
    }
}
