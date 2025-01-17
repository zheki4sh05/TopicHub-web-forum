package com.example.topichubbackend.dao.impl.user;

import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.util.factories.*;

import java.util.*;

public class AuthDao implements AuthRepository {

    private final UserDao userDao = RepositoryFactory.createUserDao();
    private final RoleDao roleDao = RepositoryFactory.createRoleDao();
    private final UserRoleDao userRoleDao = RepositoryFactory.createUserRoleDao();
    public Role findRoleByType(String type){
       return roleDao.findRoleByType(type);
    }

    public void register(User newUser, List<UserRole> userRoles) {
        userDao.save(newUser);
        userRoleDao.saveAll(userRoles);
    }

    @Override
    public List<Subscription> findSubscribesById(String id) {
        return null;
    }

    @Override
    public List<Subscription> findFollowersById(String id) {
        return null;
    }

    public Optional<User> findByEmailOrLogin(String data) {
       return userDao.findByEmailOrLogin(data);
    }

    public List<UserRole> findUserRole(UUID id) {
       return userRoleDao.findUserRole(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(UserRole userRole) {
            userRoleDao.delete(userRole);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    public Optional<User> findById(String id) {
        return userDao.findById(id);
    }

    public List<User> findAll(String id) {
       return userDao.findAll(id);
    }
}
