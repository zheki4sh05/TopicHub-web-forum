package com.example.topichubbackend.util.factories;

import com.example.topichubbackend.services.impls.*;
import com.example.topichubbackend.services.interfaces.*;

import java.io.*;
import java.security.*;

public class ServiceFactory {
    public static AuthService getAuthService(){
        var service = AuthService.getInstance();
        service.setAuthDao(RepositoryFactory.createAuthDao());
        return AuthService.getInstance();
    }

    public static SessionService getSessionService(){
        return SessionService.getInstance();
    }

    public static CommentsService getCommentsService(){
        return CommentsService.getInstance();
    }

    public static ReactionService getReactionService(){
        return ReactionService.getInstance();
    }

    public static ImageService createImageService(){return  ImageService.getInstance(); }

    public static ComplaintService createComplaintService(){return  ComplaintService.getInstance(); }

    public static HubService createHubService(){
        return HubService.getInstance();
    }

    public static ArticleService getArticleService() {
        return ArticleService.getInstance();
    }

    public static EmailService getEmailService(){
        return EmailService.getInstance();
    }

    public static IFileStorage getFileStorageService() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return new MinioService();
    }
}
