package com.example.topichubbackend.mapper.objectMapper;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;

import java.util.*;

public interface IObjectMapper {
    ArticleDto mapFrom(Article item, String dilimiter);

    ArticlePartDto mapFrom(ArticlePart item);

    UserDto mapFrom(User newUser, List<UserRole> userRole);
}
