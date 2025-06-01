package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.smartbase.dto.AttachmentDto;
import site.smartbase.entity.Attachment;
import site.smartbase.entity.Candidate;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.AttachmentRepo;
import site.smartbase.repository.CandidateRepo;
import site.smartbase.service.AttachmentService;
import site.smartbase.service.AzureBlobService;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepo attachmentRepo;
    private final ModelMapper modelMapper;
    private final AzureBlobService azureBlobService;
    private final CandidateRepo candidateRepo;

    @Override
    public AttachmentDto addAttachment(MultipartFile file, Long candidateId) {
        Candidate candidate = candidateRepo.findById(candidateId).orElseThrow(() -> new NotFoundException("Candidate not found"));
        String address = azureBlobService.uploadFile(file);
        Attachment attachment = new Attachment();
        attachment.setCandidate(candidate);
        attachment.setAttachmentPath(address);
        return modelMapper.map(attachmentRepo.save(attachment), AttachmentDto.class);
    }
}
