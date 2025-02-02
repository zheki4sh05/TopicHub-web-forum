package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.*;
import org.springframework.data.domain.*;

import java.util.*;

public interface IAuthService {
    UserDto register(UserDto userDto);

    UserDto login(AuthDto userDto);

    List<UserRole> getUserRole(UUID id);

    void updateUser(UserDto userDto, String userId);

    void delete(String userId);

    List<UserDto> findAll(String id);

    UserDto manageBlock(String authorId, String status);

    UserDto findById(String userId);

    Page<UserDto> fetch(String status, Pageable page);
}
