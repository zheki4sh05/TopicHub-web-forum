package com.example.topichubbackend.repository;

import com.example.topichubbackend.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;


public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Query("""
From Comment c where c.article.id = :articleId
""")
    List<Comment> findAllByArticleId(@Param("articleId") Long aLong);

}
