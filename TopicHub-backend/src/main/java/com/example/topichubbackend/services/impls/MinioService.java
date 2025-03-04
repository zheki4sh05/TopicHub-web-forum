package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.services.interfaces.*;
import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.security.*;
import java.util.*;

@Service
@Slf4j
public class MinioService implements IFileStorage {

    @Autowired
    private  MinioClient minioClient;

    @Value("${minio.bucket}")
    private  String BUCKET_NAME;

//    @Value("${minio.access.name}")
//    private  String login;
//
//    @Value("${minio.access.secret}")
//    private  String password;
//
//    @Value("${minio.url}")
//    private String url;

//    public MinioService(){
//        log.info("BUCKET_NAME {}", BUCKET_NAME);
//        log.info("login {}", login);
//        log.info("password {}", password);
//        log.info("url {}", url);
//
//    try{
//        this.minioClient = MinioClient.builder()
//                .endpoint(url)
//                .credentials(login, password)
//                .build();
//    }catch (RuntimeException e){
//        log.error("minio:{}", e.getMessage());
//    }
//
//
//    }

    @Override
    public boolean save(InputStream fileContent, String path) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .stream(fileContent, fileContent.available(), -1)
                            .bucket(BUCKET_NAME)
                            .object(path)
                            .build());
            return true;
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("minio service {}", e.getMessage());
            return false;
        }

    }

    @Override
    public Optional<byte[]> findByPath(String userId) {
        try (InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(userId)
                        .build()))
        {
           return Optional.of(inputStream.readAllBytes());
        } catch (IOException e) {
            log.warn("error during file download: {}", e.getMessage());
        } catch (ServerException | InsufficientDataException | ErrorResponseException | NoSuchAlgorithmException |
                 InvalidKeyException | InvalidResponseException | XmlParserException | InternalException e) {
            log.error("file server connection: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
