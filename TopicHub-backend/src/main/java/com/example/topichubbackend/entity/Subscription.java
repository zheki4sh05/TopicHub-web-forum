package com.example.topichubbackend.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscription")
@Builder
public class Subscription {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne
    @JoinColumn(name = "follower")
    private User follower;

}
