package com.example.topichubbackend.repository;

import com.example.topichubbackend.model.*;
import com.example.topichubbackend.model.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("""

select u 
from User u 
where u.email= :data or u.login= :data
""")
    Optional<User> findByEmailOrLogin(@Param("data") String data);

    @Query("""

FROM User u where u.uuid != :id

""")
     List<User> findAll(@Param("id") String id);

    Page<User> findByStatus(String status, Pageable pageable);

    @Query("""

select u 
from User u 
where u.email=:data or u.login=:data
""")
    User getByEmailOrLogin(@Param("data") String username);
}
