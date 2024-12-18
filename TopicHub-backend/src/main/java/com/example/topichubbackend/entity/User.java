package com.example.topichubbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.engine.backend.types.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="author")
public class User {
    @Column(name = "id")
    @Id
    private UUID uuid;

    @Column(name = "login")
    private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "state")
    private Boolean state;

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;

}
