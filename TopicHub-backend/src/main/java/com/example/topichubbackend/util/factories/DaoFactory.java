package com.example.topichubbackend.util.factories;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.services.impls.*;
import com.example.topichubbackend.util.*;
import jakarta.persistence.*;

public class DaoFactory {
    public static EntityManager configureManager(){
        return PersistUtil.getEntityManager();
    }

    public static ArticleDao createArticleDao(){
        return new ArticleDao(DaoFactory.configureManager());
    }
    public static HubDao createHubDao(){
        return new HubDao(DaoFactory.configureManager());
    }

    public static AuthDao createAuthDao(){
        return new AuthDao(DaoFactory.configureManager());
    }

    public static SessionDao createSessionDao(){
        return new SessionDao(DaoFactory.configureManager());
    }

    public static ReactionDao createReactionDao(){
        return new ReactionDao(DaoFactory.configureManager());
    }

    public static CommentDao createCommentDao(){
        return new CommentDao(DaoFactory.configureManager());
    }

    public static ImageDao createImageDao(){
        return new ImageDao(DaoFactory.configureManager());
    }

    public static ComplaintDao createComplaintDao(){
        return new ComplaintDao(DaoFactory.configureManager());
    }
}
