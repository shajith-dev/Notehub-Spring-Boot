package com.example.notehub.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Autowired
    public S3Service(S3Client s3Client){
        this.s3Client = s3Client;
    }

    public void uploadFile(String bucketName, String key, MultipartFile file) throws IOException {
        PutObjectResponse response = s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(file.getContentType())
                        .contentDisposition("inline")
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );
        System.out.println("File uploaded successfully to S3 bucket: " + bucketName);
    }

    public File downloadFileAsObject(String bucketName, String key) throws IOException {
        ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                software.amazon.awssdk.core.sync.ResponseTransformer.toBytes()
        );

        File tempFile = File.createTempFile("s3-", key.replaceAll("/", "_"));
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(responseBytes.asByteArray());
        }

        System.out.println("File fetched from S3 bucket: " + bucketName + ", key: " + key);
        return tempFile;
    }

}
