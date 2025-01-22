package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.impl.user.*;
import com.example.topichubbackend.dao.impl.util.*;
import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.objectMapper.*;
import com.example.topichubbackend.mapper.objectMapper.impl.*;
import com.example.topichubbackend.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    AuthRepository authDao;

    @InjectMocks
    AuthService authService;

    @Test
    void registerUser(){
        UserDto userDto = UserDto.builder()
                .login("login")
                .password("123456")
                .email("test@mail.ru")
                .build();
        User user = EntityFactory.getUserAdmin();
        Role role1 = Role.builder()
                .name(RoleDto.ADMIN.name())
                .uuid(1)
                .build();
        Role role2 =  Role.builder()
                .name(RoleDto.USER.name())
                .uuid(1)
                .build();
        List<UserRole> userRoles = EntityFactory.getUserRole(List.of(role1,role2),user);
        when(authDao.findRoleByType(RoleDto.USER.type())).thenReturn(role2);
        doNothing().when(authDao).register(user,userRoles);
        UserDto savedUser = authService.register(userDto);
        assertEquals(savedUser.getEmail(), userDto.getEmail());

    }
    @Test
    void login_user_by_email(){
        AuthDto authDto = AuthDto.builder()
                .data("test@mail.ru")
                .password("123456")
                .build();
        var id = UUID.randomUUID();
        User user = User.builder()
                .uuid(id)
                .login("login")
                .email("test@mail.ru")
                .password(PasswordEncoder.getHash("123456", id.toString()))
                .state(false)
                .build();
        doReturn(Optional.of(user)).when(authDao).findByEmailOrLogin(authDto.getData());
        Optional<User> optionalUser = authService.login(authDto);
        assertTrue(optionalUser.isPresent());
        assertEquals(optionalUser.get().getEmail(), authDto.getData());
    }

    @Test
    void login_block_user_by_email(){
        AuthDto authDto = AuthDto.builder()
                .data("test@mail.ru")
                .password("123456")
                .build();
        var id = UUID.randomUUID();
        User user = User.builder()
                .uuid(id)
                .login("login")
                .email("test@mail.ru")
                .password(PasswordEncoder.getHash("123456", id.toString()))
                .state(true)
                .build();
        doReturn(Optional.of(user)).when(authDao).findByEmailOrLogin(authDto.getData());
        assertThrows(UserBlockException.class, ()->{
            authService.login(authDto);
        });

    }
    @Test
    void login_with_incorrect_password(){
        AuthDto authDto = AuthDto.builder()
                .data("test@mail.ru")
                .password("12345")
                .build();
        var id = UUID.randomUUID();
        User user = User.builder()
                .uuid(id)
                .login("login")
                .email("test@mail.ru")
                .password(PasswordEncoder.getHash("123456", id.toString()))
                .state(false)
                .build();
        doReturn(Optional.of(user)).when(authDao).findByEmailOrLogin(authDto.getData());
        Optional<User> optionalUser =   authService.login(authDto);
        assertTrue(optionalUser.isEmpty());
    }
    @Test
    void manage_block(){
        var id = UUID.randomUUID();
        User user = User.builder()
                .uuid(id)
                .login("login")
                .email("test@mail.ru")
                .password(PasswordEncoder.getHash("123456", id.toString()))
                .state(true)
                .build();
        doReturn(Optional.of(user)).when(authDao).findById(id.toString());
        assertDoesNotThrow(()->{
            var blocked = authService.manageBlock(user.getUuid().toString());
            assertFalse(blocked.getStatus());
        });
    }
    @Test
    void manage_block_user_not_found(){
        var id = UUID.randomUUID();
        User user = User.builder()
                .uuid(id)
                .login("login")
                .email("test@mail.ru")
                .password(PasswordEncoder.getHash("123456", id.toString()))
                .state(true)
                .build();
        doReturn(Optional.empty()).when(authDao).findById(id.toString());
        assertThrows(EntityNotFoundException.class, ()->{
            var blocked = authService.manageBlock(user.getUuid().toString());
        });
    }





}