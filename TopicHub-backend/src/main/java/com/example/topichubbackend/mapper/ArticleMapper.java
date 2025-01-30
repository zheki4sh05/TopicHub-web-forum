package com.example.topichubbackend.mapper;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.*;
import org.mapstruct.*;

import java.util.*;


@Mapper(componentModel = "spring", uses = {ArticlePartMapper.class, UserMapper.class})
public interface ArticleMapper{
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(source = "hub.id", target = "hub"),
            @Mapping(source = "articlePartList", target = "list"),
            @Mapping(source = "author", target = "userDto"),
            @Mapping(ignore = true, target = "likes"),
            @Mapping(ignore = true, target = "dislikes"),
            @Mapping(ignore = true, target = "likeState"),
            @Mapping(ignore = true, target = "commentsCount"),
            @Mapping( target = "keyWords", expression = "java(getWords(item.getKeyWords()))")
    })
    ArticleDto toDto(Article item);

    default List<String> getWords(String words){
        return Arrays.asList(words.split("|"));
    }

}
