package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;

import java.util.*;

public interface ICommentsService {
    List<CommentDto> fetch(String article);

    CommentDto create(CommentDto commentDto, String userId);

    CommentDto update(CommentDto commentDto, String userId);

    void delete(String commentId, String userId);

    CommentDto findById(String targetId);
}
