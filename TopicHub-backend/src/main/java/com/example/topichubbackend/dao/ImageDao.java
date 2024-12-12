package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;

public class ImageDao extends BaseDao{

    public ImageDao(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Optional<Image> findImg(String userId) {

        String sql  = "From Image i where i.author.id= :userId";
        try {
            return Optional.of(this.em.createQuery(sql, Image.class)
                    .setParameter("userId", UUID.fromString(userId))
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }
}
