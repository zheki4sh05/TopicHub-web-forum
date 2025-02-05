package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.repository.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.servlet.http.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.time.chrono.*;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class SessionService implements ISessionService {

    private final SessionRepository sessionRepository;
//    @Override
//    public UUID createByUser(User registeredUser) {
//
//        Optional<Session> sessionWrapper = sessionRepository.findByUserId(registeredUser.getUuid());
//
//        sessionWrapper.ifPresent(sessionRepository::delete);
//
//        UUID newUUID = UUID.randomUUID();
//
//        Session session = new Session(newUUID, registeredUser, LocalDateTime.now().plusHours(2));
//        sessionRepository.save(session);
//
//        return newUUID;
//    }
//
//    @Override
//    public void delete(String value) {
//        UUID uuid = UUID.fromString(value);
//        Session session = sessionRepository.findById(uuid).get();
//        sessionRepository.delete(session);
//    }
//
//    @Override
//    public boolean isSessionActiveBy(Optional<Cookie> cookie) {
//        UUID uuid = UUID.fromString(cookie.get().getValue());
//        log.info("check session by id: {}",uuid);
//        Session session = null;
//        try {
//            session = sessionRepository.findById(uuid).orElseThrow(BadRequestException::new);
//            log.info("session: {}",session);
//        } catch (BadRequestException e) {
//            return false;
//        }
//
//        return session.getExpiresAt().isAfter(ChronoLocalDateTime.from(LocalDateTime.now()));
//    }

    @Override
    public UserDto findByToken(String principal) {
        return null;
    }
}