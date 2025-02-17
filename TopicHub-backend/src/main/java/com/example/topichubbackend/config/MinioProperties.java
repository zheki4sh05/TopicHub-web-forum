package com.example.topichubbackend.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.*;

@Component
@Getter
@Setter
public class MinioProperties {

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.url}")
    private String url;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;
}
