package com.example.topichubbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="role")
public class Role {
    @Column(name = "id")
    @Id
    private UUID uuid;

    @Column(name = "name")
    private String name;
}
