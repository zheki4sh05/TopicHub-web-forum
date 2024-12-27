package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;

import java.util.*;

public interface IAuthService {
    UserDto register(UserDto userDto);

    Optional<User> login(AuthDto userDto);

    List<UserRole> getUserRole(UUID id);

    void updateUser(UserDto userDto, String userId);

    void delete(String userId);

    List<UserDto> findAll(String id);

    void manageBlock(String authorId);

    UserDto findById(String userId);
}
