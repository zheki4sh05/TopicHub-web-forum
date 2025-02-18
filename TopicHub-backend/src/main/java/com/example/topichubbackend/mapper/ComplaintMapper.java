package com.example.topichubbackend.mapper;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.complaints.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ArticleMapper.class, AuthorMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ComplaintMapper {

    @Mapping( target = "id", expression = "java(item.getId().toString())")
    @Mapping(target = "targetId", expression = "java(\"id\")")
    @Mapping(target = "type", ignore = true)
    @Mapping(source = "date", target = "date")
    @Mapping(target = "userDto", source = "author")
    ComplaintDto mapFrom(ArticleComplaint item);


    @Mapping(target = "id", expression = "java(item.getId().toString())")
    @Mapping( target = "targetId", expression = "java(item.getComment().getId().toString())")
    @Mapping(target = "type", ignore = true)
    @Mapping(source = "date", target = "date")
    @Mapping(target = "userDto", source = "author")
    ComplaintDto mapFrom(CommentComplaint item);
}
