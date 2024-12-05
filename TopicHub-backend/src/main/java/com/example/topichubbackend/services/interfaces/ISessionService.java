package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;

import java.util.*;

public interface ISessionService {
    UUID createByUser(User registeredUser);

    void delete(String value);
}
