package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.smartbase.dto.AttachmentDto;

public interface AttachmentService {
    @Transactional
    AttachmentDto addAttachment(MultipartFile file, Long candidateId);

    @Transactional
    void deleteAttachment(Long attachmentId);
}
