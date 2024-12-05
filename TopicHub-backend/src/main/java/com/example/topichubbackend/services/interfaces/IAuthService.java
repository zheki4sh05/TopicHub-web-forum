package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;

import java.util.*;

public interface IAuthService {
    User register(UserDto userDto);

    Optional<User> find(UserDto userDto);
}
