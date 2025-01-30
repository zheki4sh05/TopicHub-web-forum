package com.example.topichubbackend.repository;

import com.example.topichubbackend.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;


public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query("""
SELECT COUNT(c.id) FROM Comment c WHERE c.article.id= :id
""")
    Long calcArticleCommentsCount(@Param("id") Long id);

    @Query("""
From Comment c where c.article.id = :articleId
""")
    List<Comment> findAllByArticleId(@Param("articleId") Long aLong);

}
