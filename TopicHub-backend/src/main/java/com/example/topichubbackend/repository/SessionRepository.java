package com.example.topichubbackend.repository;

import com.example.topichubbackend.model.*;

import java.util.*;

public interface SessionRepository {
    Optional<Session> findByUserId(UUID uuid);

    void delete(Session session);

    Session save(Session session);

    Optional<Session> findById(UUID uuid);
}
