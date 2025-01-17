package com.example.topichubbackend.dao.impl.reaction;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import jakarta.persistence.*;
import org.hibernate.exception.*;

import java.util.*;

public class BookmarkDao extends AbstractHibernateDao<UUID, Bookmark> {
    public BookmarkDao(EntityManager entityManager) {
        this.em = entityManager;
    }
    public Boolean checkMarked(String userId, String articleId) {
        Query query = this.em.createQuery("SELECT COUNT(b) > 0 FROM Bookmark b WHERE b.author.id = :authorId AND b.article.id = :articleId");
        query.setParameter("articleId", Integer.valueOf(articleId));
        query.setParameter("authorId",  UUID.fromString(userId));

        return (Boolean) query.getSingleResult();
    }
    public void addBookmark(Article article, User user) {

        try{

            Bookmark bookmark = Bookmark.builder()
                    .id(UUID.randomUUID())
                    .article(article)
                    .author(user)
                    .build();

            this.save(bookmark);

        }catch (ConstraintViolationException constraintViolationException){
            throw new BadRequestException();
        }

    }

    public void removeBookmark(String articleId, String userId) {
        Query query = this.em.createQuery("From Bookmark b where b.author.id = :userId and b.article.id = :articleId", Bookmark.class);
        query.setParameter("userId", UUID.fromString(userId));
        query.setParameter("articleId", Integer.valueOf(articleId));
        var bookmark =(Bookmark) query.getSingleResult();
        this.delete(bookmark);
    }
}
