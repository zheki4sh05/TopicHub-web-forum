package com.example.topichubbackend.repository;

import com.example.topichubbackend.model.complaints.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface ArticleComplaintRepository extends JpaRepository<ArticleComplaint, UUID> {


    @Query("""

From ArticleComplaint ac where ac.article.id = :article and ac.author.uuid = :author

""")
     Optional<ArticleComplaint> findByArticleUserId(@Param("article") String targetId, @Param("author") String userId);

    @Query("""

From ArticleComplaint ac

""")
     List<ArticleComplaint> findAllArticle();

    @Query("""

From ArticleComplaint ac where ac.id= :id

""")
     Optional<ArticleComplaint> findByIdArticle(@Param("id") String complaintId);

}
