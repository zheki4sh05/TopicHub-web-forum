package com.example.topichubbackend.mapper;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.*;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.*;

//@Mapper(componentModel = "spring", uses = AuthorMapper.class)
//public interface CommentMapper {
//
//    @Mappings({
//            @Mapping(target = "id", expression = "java(comment.getId().toString())"),
//            @Mapping(source = "message", target = "value"),
//            @Mapping(target = "articleId", expression = "java(article.getId().toString())"),
//            @Mapping(source = "parentComment.id", target = "parentId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
//    })
//    CommentDto mapFrom(Comment comment, Long articleId, Set<UUID> processedIds);
//
//    default List<CommentDto> mapReplies(List<Comment> replies, Long articleId, Set<UUID> processedIds) {
//        return replies != null ? replies.stream()
//                .map(reply -> mapFrom(reply, articleId, processedIds))
//                .collect(Collectors.toList()) : null;
//    }
//
//    default List<CommentDto> mapRepliesWithProcessedIds(List<Comment> replies, Long articleId, Set<UUID> processedIds) {
//        if (replies == null) {
//            return null;
//        }
//        return replies.stream()
//                .map(reply -> mapFrom(reply, articleId, processedIds))
//                .collect(Collectors.toList());
//    }
//}