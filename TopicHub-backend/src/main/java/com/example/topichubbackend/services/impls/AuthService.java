package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;

import java.util.*;

public class AuthService implements IAuthService {
    private final static AuthService authService = new AuthService();
    private AuthService() { }
    public static AuthService  getInstance(){
        return authService;
    }

    private final AuthDao authDao = DaoFactory.createAuthDao();

    @Override
    public User register(UserDto userDto) {


        User newUser  = User.builder()
                .uuid(UUID.randomUUID())
                .email(userDto.getEmail())
                .login(userDto.getLogin())
                .password(userDto.getPassword()).build();

         authDao.save(newUser);

         Role role = authDao.findRoleByType(RoleDto.USER.type());

         UserRole userRole = UserRole.builder()
                 .role(role)
                 .user(newUser)
                 .uuid(UUID.randomUUID())
                 .build();





        return newUser;
    }

    @Override
    public Optional<User> find(UserDto userDto) {
        User isExist = authDao.findByEmail(userDto.getEmail());
        return Optional.of(isExist);
    }
}
