package com.example.topichubbackend.config;

import lombok.*;
import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.*;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String bucket;
    private String url;
    private String accessKey;
    private String secretKey;
}
