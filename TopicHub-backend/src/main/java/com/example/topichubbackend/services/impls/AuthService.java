package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.impl.user.*;
import com.example.topichubbackend.dao.interfaces.*;
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
import lombok.extern.slf4j.*;

import java.util.*;
import java.util.stream.*;
@Slf4j
public class AuthService implements IAuthService {

    private final IObjectMapper objectMapper = new ObjectMapperImpl();
    private final static AuthService authService = new AuthService();

    public void setAuthDao(AuthRepository authDao) {
        this.authDao = authDao;
    }

    private AuthRepository authDao;
    private AuthService() { }
    public static AuthService getInstance(){
        return authService;
    }



    @Override
    public UserDto register(UserDto userDto) {
        User newUser  =prepareNewUser(userDto);
        log.info("prepared new user: {}", newUser);
        List<UserRole> userRoles = prepareUserRole(newUser);
        log.info("user roles: {}",userRoles);
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
            authDao.update(user);
        }catch (RollbackException e){
            throw new BadRequestException();
        }

    }

    @Override
    public void delete(String userId) {
        Optional<User> user = authDao.findById(userId);
        if(user.isPresent()) {
            List<UserRole> userRoles = user.get().getUserRoles();
            userRoles.forEach(authDao::delete);
            authDao.delete(user.get());
        }else{
            throw  new EntityNotFoundException(ErrorKey.NOT_FOUND.type());
        }

    }

    @Override
    public List<UserDto> findAll(String id) {
        List<User> userList = authDao.findAll(id);
        return userList.stream()
                .map(item->objectMapper.mapFrom(item, item.getUserRoles()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto manageBlock(String authorId) {
        Optional<User> user = authDao.findById(authorId);
        if(user.isPresent()){
            var item = user.get();
            item.setState(!item.getState());
            authDao.update(item);
            return objectMapper.mapFrom(item, new ArrayList<>());
        }else{
            throw  new EntityNotFoundException(ErrorKey.NOT_FOUND.type());
        }
    }

    @Override
    public UserDto findById(String userId) {
        User user = authDao.findById(userId).orElseThrow(EntityNotFoundException::new);
        return objectMapper.mapFrom(user, user.getUserRoles());
    }

    private User prepareNewUser(UserDto userDto){
        UUID uuid = UUID.randomUUID();
        return  User.builder()
                .uuid(uuid)
                .state(false)
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
        if(isExist.getState()){
            throw new UserBlockException();
        }else{
            if(isExist.getPassword().equals(PasswordEncoder.getHash(userDto.getPassword(), isExist.getUuid().toString()))){
                return Optional.of(isExist);
            }else{
                return Optional.empty();
            }
        }


    }
}
