package site.smartbase.service;

import org.springframework.web.multipart.MultipartFile;
import site.smartbase.dto.AttachmentDto;

public interface AttachmentService {
    AttachmentDto addAttachment(MultipartFile file, Long candidateId);
}
