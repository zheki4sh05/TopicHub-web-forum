package com.example.topichubbackend.dao;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import jakarta.persistence.criteria.*;

import java.util.*;

public class FilterQueryFactory {

    private final CriteriaBuilder criteriaBuilder;

    public FilterQueryFactory(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    public CriteriaQuery<Article> createQuery(ArticleFilterDto articleFilterDto, FilterJoin filterJoin, Object joinId){
        List<Predicate> predicates = new ArrayList<>();
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
        Root<Article> articleRoot = criteriaQuery.from(Article.class);
        Expression<Integer> monthExpression = criteriaBuilder.function(
                "date_part", Integer.class, criteriaBuilder.literal("month"), articleRoot.get("created")
        );
        Expression<Integer> yearExpression = criteriaBuilder.function(
                "date_part", Integer.class, criteriaBuilder.literal("year"), articleRoot.get("created")
        );
        Predicate monthPredicate = criteriaBuilder.equal(monthExpression, articleFilterDto.getMonth());
        Predicate yearPredicate = criteriaBuilder.equal(yearExpression, articleFilterDto.getYear());
        Predicate finalPredicate = criteriaBuilder.and(monthPredicate, yearPredicate);
        if(articleFilterDto.getMonth()!=null && articleFilterDto.getYear()!=null){
            predicates.add(finalPredicate);
        }


        Subquery<Long> likesSubquery = criteriaQuery.subquery(Long.class);
        Root<Likes> like = likesSubquery.from(Likes.class);
        likesSubquery.select(criteriaBuilder.coalesce(criteriaBuilder.count(like), 0L))
                .where(criteriaBuilder.equal(like.get("article"), articleRoot),
                        criteriaBuilder.equal(like.get("state"), 1));

        Subquery<Long> dislikesSubquery = criteriaQuery.subquery(Long.class);
        Root<Likes> dislike = dislikesSubquery.from(Likes.class);
        dislikesSubquery.select(criteriaBuilder.coalesce(criteriaBuilder.count(dislike), 0L))
                .where(criteriaBuilder.equal(dislike.get("article"), articleRoot),
                        criteriaBuilder.equal(dislike.get("state"), -1));

        Predicate condition = criteriaBuilder.or(
                criteriaBuilder.and(
                        criteriaBuilder.gt(dislikesSubquery, 0L),
                        criteriaBuilder.gt(criteriaBuilder.prod(criteriaBuilder.quot(likesSubquery, dislikesSubquery), 1.6), 1.0)
                ),
                criteriaBuilder.and(
                        criteriaBuilder.equal(dislikesSubquery, 0L),
                        criteriaBuilder.gt(likesSubquery, 0L)
                )
        );

        if(articleFilterDto.getRating()!=null){
            predicates.add(condition);
        }
        if(joinId!=null){
            predicates.addAll(getPredicates(filterJoin, articleRoot, joinId, criteriaQuery));
        }
        criteriaQuery.select(articleRoot)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(criteriaBuilder.desc(articleRoot.get("created")));
        return criteriaQuery;
    }
    private List<Predicate> getPredicates(FilterJoin filterJoin, Root<Article> article, Object id, CriteriaQuery<Article> criteriaQuery){
        List<Predicate> predicates  = new ArrayList<>();
        if(filterJoin.value().equals(FilterJoin.SUBSCRIPTION.value())){
            Root<Subscription> subscription = criteriaQuery.from(Subscription.class);
            Predicate joinCondition = criteriaBuilder.and(
                    criteriaBuilder.equal(subscription.get("author").get("id"), article.get("author").get("id")),
                    criteriaBuilder.equal(subscription.get("follower").get("id"), id)
);
            predicates.add(joinCondition);
            return predicates;
        }else if(filterJoin.value().equals(FilterJoin.HUB.value())){
        return null;
        }else{
        return null;
        }
    }

}
