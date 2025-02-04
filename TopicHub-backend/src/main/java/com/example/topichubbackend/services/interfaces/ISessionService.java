package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.*;
import jakarta.servlet.http.*;

import java.util.*;

public interface ISessionService {
//    UUID createByUser(User registeredUser);
//
//    void delete(String value);
//
//    boolean isSessionActiveBy(Optional<Cookie> cookie);

    UserDto findByToken(String principal);
}
