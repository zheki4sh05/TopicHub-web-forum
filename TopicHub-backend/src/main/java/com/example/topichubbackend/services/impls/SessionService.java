package com.example.topichubbackend.services.impls;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.services.interfaces.*;
import lombok.extern.slf4j.*;
import java.time.*;
import java.time.chrono.*;
import java.util.*;

//@Slf4j
//public class SessionService implements ISessionService {
//    private final static SessionService sessionService = new SessionService();
//    private SessionService() { }
//    public static SessionService  getInstance(){
//        return sessionService;
//    }
//
//    private final SessionRepository sessionDao = RepositoryFactory.createSessionDao();
//
//    @Override
//    public UUID createByUser(User registeredUser) {
//
//        Optional<Session> sessionWrapper = sessionDao.findByUserId(registeredUser.getUuid());
//
//        sessionWrapper.ifPresent(sessionDao::delete);
//
//        UUID newUUID = UUID.randomUUID();
//
//        Session session = new Session(newUUID, registeredUser, LocalDateTime.now().plusHours(2));
//        sessionDao.save(session);
//
//        return newUUID;
//    }
//
//    @Override
//    public void delete(String value) {
//        UUID uuid = UUID.fromString(value);
//        Session session = sessionDao.findById(uuid).get();
//        sessionDao.delete(session);
//    }
//
//    @Override
//    public boolean isSessionActiveBy(Optional<Cookie> cookie) {
//        UUID uuid = UUID.fromString(cookie.get().getValue());
//        log.info("check session by id: {}",uuid);
//        Session session = null;
//        try {
//            session = sessionDao.findById(uuid).orElseThrow(BadRequestException::new);
//            log.info("session: {}",session);
//        } catch (BadRequestException e) {
//            return false;
//        }
//
//        return session.getExpiresAt().isAfter(ChronoLocalDateTime.from(LocalDateTime.now()));
//    }
//}
