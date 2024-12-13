package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.exceptions.EntityNotFoundException;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import com.example.topichubbackend.util.factories.*;
import com.example.topichubbackend.mapper.objectMapper.*;
import com.example.topichubbackend.mapper.objectMapper.impl.*;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.*;

public class AuthService implements IAuthService {

    private final IObjectMapper objectMapper = new ObjectMapperImpl();
    private final static AuthService authService = new AuthService();
    private AuthService() { }
    public static AuthService  getInstance(){
        return authService;
    }

    private final AuthDao authDao = DaoFactory.createAuthDao();

    @Override
    public UserDto register(UserDto userDto) {
        User newUser  =prepareNewUser(userDto);
        List<UserRole> userRoles = prepareUserRole(newUser);
        authDao.register(newUser, userRoles);
        return objectMapper.mapFrom(newUser, userRoles);
    }

    @Override
    public Optional<User> login(AuthDto userDto) {
        Optional<User> isExist = authDao.findByEmailOrLogin(userDto.getData());
        return isExist.flatMap(user -> checkUser(user, userDto));
    }

    @Override
    public List<UserRole> getUserRole(UUID id){
        return  authDao.findUserRole(id);
    }

    @Override
    public void updateUser(UserDto userDto, String userId) {
        try{
            User user = authDao.findById(userId).orElseThrow(EntityNotFoundException::new);
            user.setEmail(userDto.getEmail());
            user.setLogin(userDto.getLogin());
            authDao.merge(user);
        }catch (RollbackException e){
            throw new BadRequestException();
        }

    }

    @Override
    public void delete(String userId) {
        User user = authDao.findById(userId).orElseThrow(EntityNotFoundException::new);
            List<UserRole> userRoles = user.getUserRoles();
            userRoles.forEach(authDao::delete);
            authDao.delete(user);
    }

    @Override
    public List<UserDto> findAll(String id) {
        List<User> userList = authDao.findAll(id);
        return userList.stream()
                .map(item->objectMapper.mapFrom(item, item.getUserRoles()))
                .collect(Collectors.toList());
    }

    @Override
    public void manageBlock(String authorId) {
        User user = authDao.findById(authorId).orElseThrow(EntityNotFoundException::new);
        if(user.getState()){
            user.setState(false);
        }else{
            user.setState(true);
        }
        authDao.merge(user);


    }

    private User prepareNewUser(UserDto userDto){

        UUID uuid = UUID.randomUUID();

        return  User.builder()
                .uuid(uuid)
                .email(userDto.getEmail())
                .login(userDto.getLogin())
                .password(PasswordEncoder.getHash(userDto.getPassword(), uuid.toString())).build();
    }
    private List<UserRole> prepareUserRole(User user){

        Role role = authDao.findRoleByType(RoleDto.USER.type());
        return List.of(UserRole.builder()
                .role(role)
                .user(user)
                .uuid(UUID.randomUUID())
                .build());
    }
    private Optional<User> checkUser(User isExist, AuthDto userDto) {

        if(isExist.getPassword().equals(PasswordEncoder.getHash(userDto.getPassword(), isExist.getUuid().toString()))){
            return Optional.of(isExist);
        }else{
            return Optional.empty();
        }

    }
}
