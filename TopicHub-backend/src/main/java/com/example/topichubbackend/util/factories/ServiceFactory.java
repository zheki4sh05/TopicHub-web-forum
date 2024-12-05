package com.example.topichubbackend.util.factories;

import com.example.topichubbackend.services.impls.*;

public class ServiceFactory {
    public static AuthService getAuthService(){
        return AuthService.getInstance();
    }

    public static SessionService getSessionService(){
        return SessionService.getInstance();
    }
}
