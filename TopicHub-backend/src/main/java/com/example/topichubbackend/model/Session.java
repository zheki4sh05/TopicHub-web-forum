package com.example.topichubbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessions")
@Builder
public class Session {
    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Column(name = "expiresat")
    private LocalDateTime expiresAt;

}
