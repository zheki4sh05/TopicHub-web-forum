package com.example.topichubbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.*;

import java.util.*;
import java.util.UUID;

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

    @Column(name = "login",unique = true)
    private String login;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "state")
    private Boolean state;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;

}
