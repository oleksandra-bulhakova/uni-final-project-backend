package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.smartbase.service.AzureBlobService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {
    private final AzureBlobService azureBlobService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String imageUrl = azureBlobService.uploadFile(file);
        return ResponseEntity.ok(imageUrl);
    }
}
