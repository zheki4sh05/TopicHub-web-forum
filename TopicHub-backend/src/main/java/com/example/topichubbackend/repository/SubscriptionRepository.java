package com.example.topichubbackend.repository;

import com.example.topichubbackend.model.*;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    @Query("""
SELECT COUNT(s) > 0 FROM Subscription s WHERE s.author.uuid = :authorId AND s.follower.id = :subscriberId


""")
    Boolean checkSubscribe(@Param("userId") UUID userId, @Param("userId") UUID authorId);

    @Query("""

From Subscription s where s.author.uuid= :authorId and s.follower.uuid = :follower

""")
    Optional<Subscription> findByUsers(@Param("authorId")UUID author, @Param("follower") UUID user);


    @Query("""
FROM Subscription s where s.follower.uuid = :id
""")
    Optional<Subscription> findSubscribesById(UUID is);

    @Query("""
FROM Subscription s where s.author.uuid = :id
""")
    List<Subscription> findFollowersById(@Param("id") String id);

    @Query("""
FROM Subscription s where s.follower.uuid = :id
""")
    List<Subscription> fetch(@Param("id") String id);
}
