package com.example.topichubbackend.mapper;

import com.example.topichubbackend.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    @Mappings({
     @Mapping(target = "id", expression = "java(UUID.randomUUID())"),
    @Mapping(target = "role", expression = "java(RoleDto.USER.type())"),
    @Mapping(target = "user", expression = "java(user)")
    })
    UserRole mapFrom(User user);

}
