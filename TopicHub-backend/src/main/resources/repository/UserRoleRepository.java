package com.example.topichubbackend.repository;

import com.example.topichubbackend.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    @Query("""

FROM UserRole ur WHERE ur.user.uuid = :id

""")
    List<UserRole> findUserRole(UUID id);
}
