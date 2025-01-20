package com.example.topichubbackend.dao.impl.user;

import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.util.factories.*;

import java.util.*;

public class AuthDao implements AuthRepository {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final UserRoleDao userRoleDao;

    public AuthDao(UserDao userDao, RoleDao roleDao, UserRoleDao userRoleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userRoleDao = userRoleDao;
    }

    public Role findRoleByType(String type){
       return roleDao.findRoleByType(type);
    }

    public void register(User newUser, List<UserRole> userRoles) {
        //userDao.save(newUser);

        List<Object> objects = new ArrayList<>();
        objects.add(newUser);
        objects.addAll(userRoles);
        userRoleDao.saveAll(objects);

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
