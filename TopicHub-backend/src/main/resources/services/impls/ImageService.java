package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.repository.*;
import com.example.topichubbackend.services.interfaces.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import java.io.*;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class ImageService implements IImageService {

    private final UserRepository authDao;
    private final IFileStorage fileStorage;

    @Override
    public byte[] fetch(String userId) {
            return fileStorage.findByPath(userId).orElseThrow(InternalServerErrorException::new);

    }

    @Override
    public void save(String userId, InputStream fileContent) throws IOException {
            User user = authDao.findById(UUID.fromString(userId)).orElseThrow(EntityNotFoundException::new);
            if(!fileStorage.save(fileContent, user.getUuid().toString())) {
                throw new InternalServerErrorException(ErrorKey.IMAGE_LOAD_ERROR.type());
            }
        }
}
