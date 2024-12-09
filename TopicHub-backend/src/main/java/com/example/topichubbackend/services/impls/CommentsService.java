package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;

import java.util.*;

public class CommentsService implements ICommentsService {

    private final static CommentsService commentsService = new CommentsService();
    private CommentsService() { }
    public static CommentsService  getInstance(){
        return commentsService;
    }

    @Override
    public List<CommentDto> fetch(String type, String article) {


        return null;
    }
}
