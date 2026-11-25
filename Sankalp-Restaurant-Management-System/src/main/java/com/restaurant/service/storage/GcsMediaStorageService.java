package com.restaurant.service.storage;

import java.io.IOException;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

/**
 * Google Cloud Storage (GCS) implementation of {@link MediaStorageService} for stateless Cloud Run deployments.
 * Authenticates via Application Default Credentials (attached Cloud Run Service Account).
 */
@Service
@ConditionalOnProperty(name = "storage.provider", havingValue = "gcs")
public class GcsMediaStorageService implements MediaStorageService {

    private static final Logger logger = LoggerFactory.getLogger(GcsMediaStorageService.class);

    @Value("${storage.gcs.bucket}")
    private String bucketName;

    @Value("${storage.gcs.prefix:uploads/}")
    private String objectPrefix;

    private Storage storage;

    @PostConstruct
    void init() {
        if (bucketName == null || bucketName.isBlank()) {
            throw new IllegalStateException("storage.gcs.bucket (GCS_BUCKET_NAME) must be configured when storage.provider=gcs");
        }
        this.storage = StorageOptions.getDefaultInstance().getService();
        logger.info("Initialized GCS media storage using bucket '{}', prefix '{}'", bucketName, normalizePrefix());
    }

    @Override
    public String store(String fileName, byte[] content, String contentType) throws IOException {
        String safeName = sanitizeFileName(fileName);
        String objectName = normalizePrefix() + safeName;
        String resolvedType = (contentType != null && !contentType.isBlank())
                ? contentType
                : detectContentType(safeName);

        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(resolvedType)
                .setCacheControl("public, max-age=86400")
                .build();

        storage.create(blobInfo, content);
        logger.info("Stored media in GCS: gs://{}/{}", bucketName, objectName);
        return safeName;
    }

    @Override
    public byte[] load(String fileName) throws IOException {
        if (fileName == null || fileName.isBlank()) {
            return null;
        }
        String safeName = sanitizeFileName(fileName);
        String objectName = normalizePrefix() + safeName;
        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        if (blob == null || !blob.exists()) {
            return null;
        }
        return blob.getContent();
    }

    private String normalizePrefix() {
        if (objectPrefix == null || objectPrefix.isBlank()) {
            return "";
        }
        String trimmed = objectPrefix.replaceAll("^/+", "");
        return trimmed.endsWith("/") ? trimmed : (trimmed + "/");
    }

    private String sanitizeFileName(String fileName) {
        if (fileName == null || fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            throw new IllegalArgumentException("Invalid file name.");
        }
        return fileName;
    }
}
