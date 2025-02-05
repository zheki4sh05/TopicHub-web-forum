package com.example.topichubbackend.mapper;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.security.dto.*;
import com.example.topichubbackend.util.*;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;

import java.util.*;
import java.util.stream.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", expression = "java(user.getUuid().toString())"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "login", target = "login"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "state", target = "state"),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "roles", qualifiedByName = "mapRolesToNames")
    })
    UserDto toDto(User user);

    @Named("mapRolesToNames")
    default List<String> mapRolesToNames(List<UserRole> userRoles) {
        return userRoles.stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());
    }

    @Mappings({
            @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "login", source = "login"),
            @Mapping(target = "status",expression = "java(com.example.topichubbackend.dto.StatusDto.ACTIVE.type())"),
            @Mapping(target = "state", ignore = true),
            @Mapping(target = "password",  qualifiedByName = "hash"),
    })
    User mapFrom(SignUpDto user);

    @Named("uuid")
    default UUID uuid() {
       return UUID.randomUUID();
    }

    @Named("hash")
    default String hash(String password) {
        return new PasswordEncoderWrapper().hash(password);
    }

}
