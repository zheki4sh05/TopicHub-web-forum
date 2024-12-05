package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;

import java.time.*;
import java.util.*;

public class SessionService implements ISessionService {
    private final static SessionService sessionService = new SessionService();
    private SessionService() { }
    public static SessionService  getInstance(){
        return sessionService;
    }

    private final SessionDao sessionDao = DaoFactory.createSessionDao();

    @Override
    public UUID createByUser(User registeredUser) {
        UUID newUUID = UUID.randomUUID();

        Session session = new Session(newUUID, registeredUser, LocalDateTime.now().plusHours(2));
        sessionDao.save(session);

        return newUUID;
    }

    @Override
    public void delete(String value) {
        UUID uuid = UUID.fromString(value);
        Session session = sessionDao.findById(uuid).get();
        sessionDao.delete(session);
    }
}
