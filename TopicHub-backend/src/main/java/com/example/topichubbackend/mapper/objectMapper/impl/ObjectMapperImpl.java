package com.example.topichubbackend.mapper.objectMapper.impl;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.mapper.objectMapper.*;

import java.util.*;
import java.util.stream.*;

public class ObjectMapperImpl implements IObjectMapper {
    @Override
    public ArticleDto mapFrom(Article item, String delimiter) {

        return ArticleDto.builder()
                .created(item.getCreated())
                .id(item.getId())
                .theme(item.getTheme())
                .likes(item.getLikes())
                .dislikes(item.getDislikes())
                .hub(item.getHub().getId())
                .keyWords(Arrays.stream(item.getKeyWords().split(delimiter)).collect(Collectors.toList()))
                .build();
    }

    @Override
    public ArticlePartDto mapFrom(ArticlePart item) {

        return ArticlePartDto.builder()
                .id(item.getId())
                .uuid(item.getUuid().toString())
                .value(item.getValue())
                .name(item.getName())
                .type(item.getType())
                .build();



    }

    @Override
    public UserDto mapFrom(User newUser, List<UserRole> userRoles) {
        return UserDto.builder()
                .id(newUser.getUuid().toString())
                .email(newUser.getEmail())
                .login(newUser.getLogin())
                .roles(userRoles.stream().map(item->item.getRole().getName()).collect(Collectors.toList()))
                .build();
    }
}
