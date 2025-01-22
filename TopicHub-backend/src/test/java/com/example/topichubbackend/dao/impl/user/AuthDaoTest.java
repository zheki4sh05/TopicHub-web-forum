package com.example.topichubbackend.dao.impl.user;
import com.example.topichubbackend.dao.impl.util.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.EntityNotFoundException;
import org.hibernate.exception.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;


class AuthDaoTest {

    UserDao userDao = new UserDao( PersistUtilTest.configureManager());

    RoleDao roleDao = new RoleDao( PersistUtilTest.configureManager());

    UserRoleDao userRoleDao = new UserRoleDao( PersistUtilTest.configureManager());

    AuthDao authDao = new AuthDao(userDao, roleDao, userRoleDao);

    @BeforeAll
    static void create(){
        PersistUtilTest.createSession();
    }

    @AfterAll
    static void dropDb(){
        PersistUtilTest.dropDb();
    }


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
        User user2 = EntityFactory.getUserAdmin();
        assertThrows(ConstraintViolationException.class, ()->{
            var admin=  roleDao.findRoleByType(RoleDto.ADMIN.type());
            var author=  roleDao.findRoleByType(RoleDto.USER.type());
            List<UserRole> userRoles2 = EntityFactory.getUserRole(List.of(author,admin) ,user2);
            authDao.register(user2, userRoles2);
        });
    }

    @Test
    void find_by_email(){
        User user1 = EntityFactory.getUserAdmin();

            var found = userDao.findByEmailOrLogin(user1.getEmail());
            assertTrue(found.isPresent());
            assertEquals(user1.getEmail(), found.get().getEmail());
        };
    @Test
    void find_by_login(){
        User user1 = EntityFactory.getUserAdmin();
        var found = userDao.findByEmailOrLogin(user1.getLogin());
        assertTrue(found.isPresent());
        assertEquals(user1.getLogin(), found.get().getLogin());
    };

}