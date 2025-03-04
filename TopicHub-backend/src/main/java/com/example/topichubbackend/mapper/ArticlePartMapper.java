package com.example.topichubbackend.mapper;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.*;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface ArticlePartMapper {


    @Mappings({
            @Mapping(target = "uuid", expression = "java(articlePart.getUuid().toString())"),
            @Mapping(source = "value", target = "value"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "created", target = "created")
    })
    ArticlePartDto toDto(ArticlePart articlePart);

    @Mappings({
            @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())"),
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "value", source = "value"),
            @Mapping(target = "name", source = "value"),
            @Mapping(target = "type", source = "type"),
            @Mapping(target = "created", source = "created"),
            @Mapping(target = "article", ignore = true),
            @Mapping(target = "articleEntity", ignore = true)

    })
    ArticlePart fromDto(ArticlePartDto articlePartDto);





}
