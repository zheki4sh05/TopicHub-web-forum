package com.example.topichubbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.*;
import java.time.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expiresat")
    private LocalDateTime expiresAt;

}
