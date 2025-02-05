package com.example.topichubbackend.filters;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.dto.filter.*;
import com.example.topichubbackend.model.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class FilterQueryFactory {
//
//    @PersistenceContext
    private EntityManager em;


    private CriteriaBuilder criteriaBuilder;

    private Predicate createLikesPredicate(Root<Article> articleRoot, Double rating){
        Expression<Long> likes = articleRoot.get("likes");
        Expression<Long> dislikes = articleRoot.get("dislikes");
        Expression<Number> ratio = criteriaBuilder.quot(likes, dislikes);
        return criteriaBuilder.gt(ratio, rating);
    }

    public List<Predicate> createPredicates(IFactoryFilterDataSupplier factoryFilterDataSupplier, Root<Article> articleRoot){

        Expression<Integer> monthExpression = criteriaBuilder.function(
                "date_part", Integer.class, criteriaBuilder.literal("month"),articleRoot.get("created")
        );
        Expression<Integer> yearExpression = criteriaBuilder.function(
                "date_part", Integer.class, criteriaBuilder.literal("year"), articleRoot.get("created")
        );
        Predicate monthPredicate = criteriaBuilder.equal(monthExpression, factoryFilterDataSupplier.getMonth());
        Predicate yearPredicate = criteriaBuilder.equal(yearExpression, factoryFilterDataSupplier.getYear());
        List<Predicate> list  = new ArrayList<>();
        list.add(monthPredicate);
        list.add(yearPredicate);
        return list;
    }

    public CriteriaQuery<Article> createQuery(ArticleFilterDto articleFilterDto){
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
        Root<Article> articleRoot = criteriaQuery.from(Article.class);
        List<Predicate> predicates = new ArrayList<>();
        if(articleFilterDto.getMonth()!=null && articleFilterDto.getYear()!=null){
            predicates.addAll(createPredicates(articleFilterDto, articleRoot));
        }
        Predicate status = createStatusPredicate(criteriaBuilder, articleRoot, articleFilterDto.getStatus());
        predicates.add(status);
        if(articleFilterDto.getRating()!=null){
            predicates.add(createLikesPredicate(articleRoot, Double.valueOf(articleFilterDto.getRating())));
        }
        List<Predicate> joins = createJoins(articleFilterDto, criteriaQuery, articleRoot);
        predicates.addAll(joins);
        criteriaQuery.select(articleRoot)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(criteriaBuilder.asc(articleRoot.get("created")));
        return criteriaQuery;
    }

    private Predicate createStatusPredicate(CriteriaBuilder criteriaBuilder, Root<Article> articleRoot, String value) {
        return criteriaBuilder.equal(articleRoot.get("status"), value);
    }

    public List<Predicate> createJoins(IBusinessLogicFilterSupplier articleFilter, CriteriaQuery<Article> criteriaQuery, Root<Article> articleRoot) {
        List<Predicate> predicates  = new ArrayList<>();
        if(articleFilter.getParam() == null){
            return new ArrayList<>();
        }
        if( articleFilter.getParam()==-1){
            Root<Subscription> subscription = criteriaQuery.from(Subscription.class);
            Predicate joinCondition = criteriaBuilder.and(
                    criteriaBuilder.equal(subscription.get("author").get("id"), articleRoot.get("author").get("id")),
                    criteriaBuilder.equal(subscription.get("follower").get("id"), "1")
            );
            predicates.add(joinCondition);
        }else if (articleFilter.getParam()==0){

        }else{

        }


        return new ArrayList<>();
    }
}
