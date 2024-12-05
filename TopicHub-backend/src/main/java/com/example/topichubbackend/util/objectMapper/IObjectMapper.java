package com.example.topichubbackend.util.objectMapper;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;

public interface IObjectMapper {
    ArticleDto mapFrom(Article item, String dilimiter);

    ArticlePartDto mapFrom(ArticlePart item);
}
