package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;

import java.util.*;

public interface IAuthService {
    UserDto register(UserDto userDto);

    Optional<User> login(AuthDto userDto);

    List<UserRole> getUserRole(UUID id);
}
