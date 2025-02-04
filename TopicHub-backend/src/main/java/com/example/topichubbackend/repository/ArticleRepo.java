package com.example.topichubbackend.repository;

import com.example.topichubbackend.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ArticleRepo extends JpaRepository<ArticleEntity, Long> {

}
