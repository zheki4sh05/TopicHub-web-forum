package com.example.topichubbackend.util.factories;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.util.*;
import jakarta.persistence.*;

public class DaoFactory {
    public static EntityManager configureManager(){
        return PersistUtil.getEntityManager();
    }

    public static ArticleDao createArticleDao(){
        return new ArticleDao(DaoFactory.configureManager());
    }
}
