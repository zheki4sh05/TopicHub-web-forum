package com.example.topichubbackend.dao.impl.user;


import com.example.topichubbackend.dao.impl.util.*;
import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.exceptions.EntityNotFoundException;
import jakarta.persistence.*;
import org.hibernate.exception.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class AuthDaoTest {

    UserDao userDao = new UserDao( PersistUtilTest.configureManager());

    RoleDao roleDao = new RoleDao( PersistUtilTest.configureManager());

    UserRoleDao userRoleDao = new UserRoleDao( PersistUtilTest.configureManager());

    AuthDao authDao = new AuthDao(userDao, roleDao, userRoleDao);



    @Test
    void register(){
            User user = EntityFactory.getUserAdmin();
            assertDoesNotThrow(()->{
                var admin=  roleDao.save(Role.builder()
                                .uuid(1)
                                .name(RoleDto.ADMIN.name())
                        .build());
                var author =  roleDao.save(
                        Role.builder()
                                .uuid(2)
                                .name(RoleDto.USER.name())
                                .build()
                );
                List<UserRole> userRoles = EntityFactory.getUserRole(List.of(author,admin) ,user);
                authDao.register(user, userRoles);
                User registered = authDao.findById(user.getUuid().toString()).orElseThrow(EntityNotFoundException::new);
                assertEquals(user.getUuid().toString(), registered.getUuid().toString());
            });
    }
    @Test
    void  register_and_unique_exception(){
        User user1 = EntityFactory.getUserAdmin();
        User user2 = EntityFactory.getUserAdmin();
        assertThrows(ConstraintViolationException.class, ()->{
            var admin=  roleDao.save(Role.builder()
                    .uuid(1)
                    .name(RoleDto.ADMIN.name())
                    .build());
            var author =  roleDao.save(
                    Role.builder()
                            .uuid(2)
                            .name(RoleDto.USER.name())
                            .build()
            );
            List<UserRole> userRoles1 = EntityFactory.getUserRole(List.of(author,admin) ,user1);
            List<UserRole> userRoles2 = EntityFactory.getUserRole(List.of(author,admin) ,user2);
            authDao.register(user1, userRoles1);
            authDao.register(user2, userRoles2);

        });
    }


}