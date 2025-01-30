package com.example.topichubbackend.repository;

import com.example.topichubbackend.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ArticlePartRepository extends JpaRepository<ArticlePart, UUID>{

    @Query("""
FROM ArticlePart ap WHERE ap.article.id = :id
""")
    List<ArticlePart> findByArticleId(@Param("id") Long id);

}
