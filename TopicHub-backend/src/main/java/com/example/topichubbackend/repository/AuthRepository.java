package com.example.topichubbackend.repository;

import com.example.topichubbackend.model.*;

import java.util.*;

public interface AuthRepository {
    Optional<User> findById(String id);

    Optional<User> findByEmailOrLogin(String data);

    List<UserRole> findUserRole(UUID id);

    void update(User user);

    void delete(UserRole userRole);

    void delete(User user);

    List<User> findAll(String id);
    void register(User newUser, List<UserRole> userRoles);

}
