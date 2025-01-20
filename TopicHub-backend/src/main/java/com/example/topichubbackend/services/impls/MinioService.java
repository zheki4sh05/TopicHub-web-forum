package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.services.interfaces.*;
import io.minio.*;
import io.minio.errors.*;

import java.io.*;
import java.security.*;
import java.util.*;

public class MinioService implements IFileStorage {
    private final MinioClient minioClient;

    private final String BUCKET_NAME = "image";
    public MinioService() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        this.minioClient = MinioClient.builder()
                .endpoint("http://192.168.0.5:9000")
                .credentials("minioadmin", "minioadmin")
                .build();

    }

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
            System.err.println("Ошибка при скачивании файла: " + e.getMessage());
        } catch (ServerException | InsufficientDataException | ErrorResponseException | NoSuchAlgorithmException |
                 InvalidKeyException | InvalidResponseException | XmlParserException | InternalException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
