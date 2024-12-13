package com.example.topichubbackend.mapper.objectMapper.impl;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.entity.complaints.*;
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
                .userDto(mapFrom(item.getAuthor(),List.of(UserRole.builder()
                                .role(Role.builder()
                                        .name(RoleDto.USER.name())
                                        .build())
                        .build())))
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
                .status(newUser.getState())
                .roles(userRoles.stream()
                        .map(item->item.getRole().getName())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public CommentDto mapFrom(Comment comment, Long aLong, Set<UUID> processedIds) {

        if (processedIds.contains(comment.getId())) {
            return null;
        }
        processedIds.add(comment.getId());

        CommentDto dto = CommentDto.builder()
                .created(comment.getCreated())
                .id(comment.getId().toString())
                .value(comment.getMessage())
                .articleId(aLong)
                .authorDto(mapFrom(comment.getAuthor()))
                .parentId(comment.getParentComment()!=null ? comment.getParentComment().getId().toString() : null)
                .build();


        if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            List<CommentDto> replyDtos = new ArrayList<>();
            for (Comment reply : comment.getReplies()) {

                replyDtos.add(mapFrom(reply, aLong, processedIds));
            }
            dto.setReplies(replyDtos);
        }

        return dto;
    }

    @Override
    public ComplaintDto mapFrom(ArticleComplaint item) {
        return ComplaintDto.builder()
                .id(item.getId().toString())
                .title(item.getTitle())
                .body(item.getBody())
                .targetId(item.getArticle().getId().toString())
                .build();
    }

    @Override
    public ComplaintDto mapFrom(CommentComplaint item) {
        return ComplaintDto.builder()
                .id(item.getId().toString())
                .title(item.getTitle())
                .body(item.getBody())
                .targetId(item.getComment().getId().toString())
                .build();
    }

    private AuthorDto mapFrom(User user){
        return AuthorDto.builder()
                .id(user.getUuid().toString())
                .email(user.getEmail())
                .login(user.getLogin())
                .build();
    }

}
