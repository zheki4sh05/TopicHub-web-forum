package com.example.topichubbackend.mapper;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.complaints.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = ArticleMapper.class, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ComplaintMapper {

    @Mapping( target = "id", expression = "java(item.getId().toString())")
    @Mapping(source = "article.id", target = "targetId")
    @Mapping(source = "article", target = "articleDto")
    @Mapping(target = "type", ignore = true)
    ComplaintDto mapFrom(ArticleComplaint item);


    @Mapping(target = "id", expression = "java(item.getId().toString())")
    @Mapping( target = "targetId", expression = "java(item.getComment().getId().toString())")
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "articleDto", ignore = true)
    ComplaintDto mapFrom(CommentComplaint item);
}
