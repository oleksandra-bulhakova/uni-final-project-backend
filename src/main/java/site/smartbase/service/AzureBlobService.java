package site.smartbase.service;

import org.springframework.web.multipart.MultipartFile;

public interface AzureBlobService {
    String uploadFile(MultipartFile file);
}
