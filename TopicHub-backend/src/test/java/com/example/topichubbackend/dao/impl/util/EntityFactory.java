package com.example.topichubbackend.dao.impl.util;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.util.*;

import java.util.*;

public class EntityFactory {

    public static User getUserAdmin(){
        return User.builder()
                .state(false)
                .uuid(UUID.randomUUID())
                .email("test@mail.ru")
                .login("login")
                .password("123456")
                .build();
    }
    public static List<UserRole> getUserRole(List<Role> roles, User user){
        return List.of(
                UserRole.builder()
                .role(roles.get(0))
                .user(user)
                .uuid(UUID.randomUUID())
                .build(),
                UserRole.builder()
                        .role(roles.get(1))
                        .user(user)
                        .uuid(UUID.randomUUID())
                        .build()
                );
    }






}
