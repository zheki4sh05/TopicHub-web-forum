package com.example.topichubbackend.dao.interfaces;

import com.example.topichubbackend.entity.*;

import java.util.*;

public interface SessionRepository {
    Optional<Session> findByUserId(UUID uuid);

    void delete(Session session);

    Session save(Session session);

    Optional<Session> findById(UUID uuid);
}
