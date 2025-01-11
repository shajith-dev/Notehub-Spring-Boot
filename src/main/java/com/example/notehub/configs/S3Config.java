package com.example.notehub.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client(){
        return S3Client.builder()
                .region(Region.of(System.getProperty("aws.region")))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                System.getProperty("aws.accessKeyId"),
                                System.getProperty("aws.secretKey")
                        )
                ))
                .build();
    }
}
