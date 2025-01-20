package com.example.topichubbackend.util.factories;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dao.impl.*;
import com.example.topichubbackend.dao.impl.article.*;
import com.example.topichubbackend.dao.impl.complaint.*;
import com.example.topichubbackend.dao.impl.reaction.*;
import com.example.topichubbackend.dao.impl.user.*;
import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.util.*;
import jakarta.persistence.*;

public class RepositoryFactory {
    public static EntityManager configureManager(){
        return PersistUtil.getEntityManager();
    }
    public static ArticleRepository createArticleDao(){
        return new ArticleDao(RepositoryFactory.configureManager());
    }
    public static HubRepository createHubDao(){
        return new HubDao(RepositoryFactory.configureManager());
    }

    public static AuthRepository createAuthDao(){
        return new AuthDao(createUserDao(), createRoleDao(), createUserRoleDao());
    }

    public static SessionRepository createSessionDao(){
        return new SessionDao(RepositoryFactory.configureManager());
    }

    public static ReactionRepository createReactionDao(){
        return new ReactionDao();
    }

    public static CommentRepository createCommentDao(){
        return new CommentDao(RepositoryFactory.configureManager());
    }

    public static ImageRepository createImageDao(){
        return new ImageDao(RepositoryFactory.configureManager());
    }

    public static ComplaintRepository createComplaintDao(){
        return new ComplaintDao();
    }

    public static LikeDao createLikeDao() {
        return new LikeDao(RepositoryFactory.configureManager());
    }

    public static SubscriptionDao createSubscriptionDao() {
        return new SubscriptionDao(RepositoryFactory.configureManager());
    }

    public static BookmarkDao createBookMarkDao() {
        return new BookmarkDao(RepositoryFactory.configureManager());
    }

    public static CommentComplaintDao createCommentComplaintDao() {
        return new CommentComplaintDao(RepositoryFactory.configureManager());
    }

    public static ArticleComplaintDao createArticleComplaintDao() {
        return new ArticleComplaintDao(RepositoryFactory.configureManager());
    }

    public static UserDao createUserDao() {
        return new UserDao(RepositoryFactory.configureManager());
    }

    public static RoleDao createRoleDao() {
        return new RoleDao(RepositoryFactory.configureManager());
    }

    public static UserRoleDao createUserRoleDao() {
        return new UserRoleDao(RepositoryFactory.configureManager());
    }

    public static ArticlePartDao createArticlePartDao() {
        return new ArticlePartDao(RepositoryFactory.configureManager());
    }
}
