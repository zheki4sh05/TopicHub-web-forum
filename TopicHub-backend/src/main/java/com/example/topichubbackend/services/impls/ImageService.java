package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import lombok.extern.slf4j.*;

import java.io.*;
import java.security.*;
import java.util.*;

@Slf4j
public class ImageService implements IImageService {

    private final static ImageService imageService = new ImageService();
    private ImageService() { }
    public static ImageService  getInstance(){
        return imageService;
    }

    private final ImageRepository imageDao = RepositoryFactory.createImageDao();
    private final AuthRepository authDao = RepositoryFactory.createAuthDao();
    private final IFileStorage fileStorage;

    {
        try {
            fileStorage = ServiceFactory.getFileStorageService();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("minio storage error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public byte[] fetch(String userId) {
//        Image image = imageDao.findImg(userId).orElseThrow(EntityNotFoundException::new);
//        return image.getData();
            return fileStorage.findByPath(userId).orElseThrow(InternalServerErrorException::new);

    }

    @Override
    public void save(String userId, InputStream fileContent) throws IOException {
//        Optional<Image> image = imageDao.findImg(userId);
//        if(image.isPresent()){
//            image.get().setData(fileContent.readAllBytes());
//            imageDao.update(image.get());
//        }else{
            User user = authDao.findById(userId).orElseThrow(EntityNotFoundException::new);
           // var saved = imageDao.save(newImage);
            if(!fileStorage.save(fileContent, user.getUuid().toString())) {
              //  imageDao.delete(saved.getId().toString());
                throw new InternalServerErrorException(ErrorKey.IMAGE_LOAD_ERROR.type());
            }

        }

}
