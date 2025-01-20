package com.example.topichubbackend.dao.interfaces;

import com.example.topichubbackend.entity.*;

import java.util.*;

public interface ImageRepository {
    Optional<Image> findImg(String userId);

    void update(Image image);

    Image save(Image newImage);

    void delete(String string);
}
