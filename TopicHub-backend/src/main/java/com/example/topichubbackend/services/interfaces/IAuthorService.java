package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;
import org.springframework.data.domain.*;

public interface IAuthorService {

    void updateUser(UserDto userDto, String userId);

    void delete(String userId);


    UserDto manageBlock(String authorId, String status);

    UserDto findById(String userId);

    PageResponse<UserDto> fetch(String status, Pageable page);

   PageResponse<UserDto> search(String login, String email, Integer page, Boolean isAdmin);

}
