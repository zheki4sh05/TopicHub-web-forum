package com.example.topichubbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="user_role")
public class UserRole {

    @Column(name = "id")
    @Id
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;


    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

}
