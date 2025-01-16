package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.entity.*;

public interface IEmailService {
    void sendCommentNotification(Comment comment, User author);
}
