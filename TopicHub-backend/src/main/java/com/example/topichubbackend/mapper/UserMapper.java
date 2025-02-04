package com.example.topichubbackend.mapper;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.*;
import org.mapstruct.*;

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
}
