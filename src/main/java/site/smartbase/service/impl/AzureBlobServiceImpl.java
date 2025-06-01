package site.smartbase.service.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import site.smartbase.exception.AzureException;
import site.smartbase.service.AzureBlobService;
import com.azure.storage.blob.models.BlobHttpHeaders;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AzureBlobServiceImpl implements AzureBlobService {
    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            if (!containerClient.exists()) {
                containerClient.create();
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            BlobClient blobClient = containerClient.getBlobClient(fileName);

            blobClient.upload(file.getInputStream(), file.getSize(), true);
            BlobHttpHeaders headers = new BlobHttpHeaders()
                    .setContentType(file.getContentType());
            blobClient.setHttpHeaders(headers);

            return blobClient.getBlobUrl();
        } catch (IOException e) {
            throw new AzureException("Failed to upload file");
        }
    }
}
