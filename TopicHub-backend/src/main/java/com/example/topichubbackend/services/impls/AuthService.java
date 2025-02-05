package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.exceptions.EntityNotFoundException;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.repository.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.persistence.RollbackException;
import jakarta.transaction.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.*;
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
    public Page<UserDto> fetch(String status, Pageable page) {
      Page<User> userList = userRepository.findByStatus(status,page);
        log.info("users list by status:"+status+" {}",userList.getContent());
        return userList.map(userMapper::toDto);
    }


}
