package com.example.topichubbackend.mapper;

import com.example.topichubbackend.model.*;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    @Mappings({
            @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())"),
    @Mapping(target = "role", expression = "java(com.example.topichubbackend.dto.RoleDto.USER.name())"),
    @Mapping(target = "user", expression = "java(user)")
    })
    UserRole mapFrom(User user);

    @Named("uuid")
    default UUID uuid() {
        return UUID.randomUUID();
    }


}
