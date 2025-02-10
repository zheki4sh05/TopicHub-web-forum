package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.exceptions.EntityNotFoundException;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.repository.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.persistence.RollbackException;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import java.util.*;
import java.util.stream.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthorService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;
    private final AuthorMapper authorMapper;
    @Override
    public void updateUser(UserDto userDto, String userId) {
        try{
            User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(EntityNotFoundException::new);
            user.setEmail(userDto.getEmail());
            user.setLogin(userDto.getLogin());
            userRepository.save(user);
        }catch (RollbackException e){
            throw new BadRequestException();
        }
    }

    @Override
    public void delete(String userId) {
        Optional<User> user = userRepository.findById(UUID.fromString(userId));
        if(user.isPresent()) {
            List<UserRole> userRoles = user.get().getRoles();
            userRoleRepository.deleteAll(userRoles);
            userRepository.delete(user.get());
        }else{
            throw  new EntityNotFoundException(ErrorKey.NOT_FOUND.type());
        }

    }

    @Override
    public List<UserDto> findAll(String id) {
        List<User> userList = userRepository.findAll(id);
        return userList.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto manageBlock(String authorId, String status) {
        Optional<User> user = userRepository.findById(UUID.fromString(authorId));
        if(user.isPresent()){
            var item = user.get();
            item.setState(!item.getState());
            item.setStatus(status);
            userRepository.save(item);
            return userMapper.toDto(item);
        }else{
            throw  new EntityNotFoundException(ErrorKey.NOT_FOUND.type());
        }
    }

    @Override
    public UserDto findById(String userId) {
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(EntityNotFoundException::new);
        return userMapper.toDto(user);
    }

    @Override
    public PageResponse<UserDto> fetch(String status, Pageable page) {
      Page<User> userList = userRepository.findByStatus(status,page);
        return PageResponse.map(userMapper::toDto, userList);
    }

    @Override
    public PageResponse<UserDto> search(String login, String email, Integer page, Boolean isAdmin) {
        Page<User> userList = userRepository.searchByLoginOrEmail(login,email, PageRequest.of(page-1,15));
        if(isAdmin){
            return PageResponse.map(userMapper::toDto, userList);
        }else{
            return PageResponse.map(userMapper::toAuthor, userList);
        }
    }



}
