package com.example.topichubbackend.entity.complaints;

import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import java.util.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Indexed
@Table(name="complaint_comment")
public class CommentComplaint {
    @Column(name = "id")
    @Id
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private User author;

}
