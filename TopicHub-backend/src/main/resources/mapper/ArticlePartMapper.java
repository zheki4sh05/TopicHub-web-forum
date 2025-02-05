package com.example.topichubbackend.mapper;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.*;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface ArticlePartMapper {


    @Mapping(target = "uuid", expression = "java(articlePart.getUuid().toString())")
    @Mapping(source = "value", target = "value")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "created", target = "created")
    ArticlePartDto toDto(ArticlePart articlePart);



}
