package com.example.topichubbackend.config;

import com.example.topichubbackend.services.impls.*;
import io.minio.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.*;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {

    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {

        return MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getAccessKey(),
                        minioProperties.getSecretKey())
                .build();
    }

    @Bean
    public CriteriaBuilder criteriaBuilder(EntityManager entityManager){
        return entityManager.getCriteriaBuilder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
}
