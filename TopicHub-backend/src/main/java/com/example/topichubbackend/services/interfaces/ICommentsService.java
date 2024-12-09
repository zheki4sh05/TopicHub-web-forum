package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;

import java.util.*;

public interface ICommentsService {
    List<CommentDto> fetch(String type, String article);
}
