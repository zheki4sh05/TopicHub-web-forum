package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.persistence.*;

import java.io.*;
import java.util.*;

public class ImageService implements IImageService {

    private final static ImageService imageService = new ImageService();
    private ImageService() { }
    public static ImageService  getInstance(){
        return imageService;
    }

    private final ImageRepository imageDao = RepositoryFactory.createImageDao();
    private final AuthRepository authDao = RepositoryFactory.createAuthDao();


    @Override
    public byte[] fetch(String userId) {
        Image image = imageDao.findImg(userId).orElseThrow(EntityNotFoundException::new);
        return image.getImageData();
    }

    @Override
    public void save(String userId, InputStream fileContent) throws IOException {
        Optional<Image> image = imageDao.findImg(userId);
        if(image.isPresent()){
            image.get().setImageData(fileContent.readAllBytes());
            imageDao.update(image.get());
        }else{
            User user = authDao.findById(userId).orElseThrow(EntityNotFoundException::new);
            var b = fileContent.readAllBytes();
            Image newImage = Image.builder()
                    .imageData(b)
                    .id(UUID.randomUUID())
                    .author(user)
                    .build();
            imageDao.save(newImage);
        }
    }
}
