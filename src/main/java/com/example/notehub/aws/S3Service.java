package com.example.notehub.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Autowired
    public S3Service(S3Client s3Client){
        this.s3Client = s3Client;
    }

    public void uploadFile(String bucketName, String key, MultipartFile file) throws IOException {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );
        System.out.println("File uploaded successfully to S3 bucket: " + bucketName);
    }

    public void downloadFile(String bucketName, String key, Path destinationPath) {
        s3Client.getObject(
                software.amazon.awssdk.services.s3.model.GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                destinationPath
        );
        System.out.println("File downloaded successfully from S3 bucket: " + bucketName);
    }
}
