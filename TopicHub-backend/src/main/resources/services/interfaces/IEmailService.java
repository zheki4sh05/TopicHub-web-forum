package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.model.*;

public interface IEmailService {
    void sendCommentNotification(Comment comment, User author);
}
