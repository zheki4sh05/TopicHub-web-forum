package com.example.topichubbackend.dao.impl.complaint;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.entity.complaints.*;
import jakarta.persistence.*;

import java.util.*;

public class CommentComplaintDao extends AbstractHibernateDao<UUID, CommentComplaint> {
    public CommentComplaintDao(EntityManager entityManager) {
        this.em = entityManager;
    }
    public Optional<CommentComplaint> findByCommentUserId(String targetId, String userId) {
        try{
            String sql = "From CommentComplaint ac where ac.comment.id = :article and ac.author.id = :author";

            Query query = this.em.createQuery(sql, CommentComplaint.class);
            query.setParameter("author", UUID.fromString(userId));
            query.setParameter("comment", targetId);
            CommentComplaint result =(CommentComplaint) query.getSingleResult();
            return Optional.ofNullable(result);
        }catch (NoResultException e){
            return Optional.empty();
        }
    }
    public List<CommentComplaint> findAllComment() {
        try{
            String sql = "From CommentComplaint  cc";
            Query query = this.em.createQuery(sql, CommentComplaint.class);
            List<CommentComplaint> result =(List<CommentComplaint>) query.getResultList();
            return result;
        }catch (NoResultException e){
            return new ArrayList<>();
        }
    }
    public Optional<CommentComplaint> findByIdComment(String complaintId) {
        try{
            String sql = "From CommentComplaint cc where cc.id= :id";
            Query query = this.em.createQuery(sql, CommentComplaint.class);
            query.setParameter("id", UUID.fromString(complaintId));
            CommentComplaint result = (CommentComplaint) query.getSingleResult();
            return Optional.ofNullable(result);
        }catch (NoResultException e){
            return Optional.empty();
        }
    }
}
