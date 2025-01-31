package com.example.topichubbackend.repository;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.dto.filter.*;
import com.example.topichubbackend.model.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@AllArgsConstructor
public class FilterQueryFactory {
    private final CriteriaBuilder criteriaBuilder;


    private Predicate createLikesPredicate(CriteriaQuery<Article> criteriaQuery, Root<Article> articleRoot){
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

        return criteriaBuilder.or(
                criteriaBuilder.and(
                        criteriaBuilder.gt(dislikesSubquery, 0L),
                        criteriaBuilder.gt(criteriaBuilder.prod(criteriaBuilder.quot(likesSubquery, dislikesSubquery), 1.6), 1.0)
                ),
                criteriaBuilder.and(
                        criteriaBuilder.equal(dislikesSubquery, 0L),
                        criteriaBuilder.gt(likesSubquery, 0L)
                )
        );
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
        return List.of(monthPredicate, yearPredicate);
    }

    public CriteriaQuery<Article> createQuery(ArticleFilterDto articleFilterDto){
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
        Root<Article> articleRoot = criteriaQuery.from(Article.class);
        List<Predicate> predicates = createPredicates(articleFilterDto, articleRoot);
        var likes = List.of(createLikesPredicate(criteriaQuery,articleRoot));
        List<Predicate> joins = createJoins(articleFilterDto, criteriaQuery, articleRoot);
        predicates.addAll(likes);
        predicates.addAll(joins);

//        Join<Article, Hub> hubJoin = articleRoot.join("hub", JoinType.INNER);  // Используется INNER JOIN по умолчанию
//
//
//        Expression<Integer> monthExpression = criteriaBuilder.function(
//                "date_part", Integer.class, criteriaBuilder.literal("month"), articleRoot.get("created")
//        );
//        Expression<Integer> yearExpression = criteriaBuilder.function(
//                "date_part", Integer.class, criteriaBuilder.literal("year"), articleRoot.get("created")
//        );
//        Predicate monthPredicate = criteriaBuilder.equal(monthExpression, articleFilterDto.getMonth());
//        Predicate yearPredicate = criteriaBuilder.equal(yearExpression, articleFilterDto.getYear());
//        Predicate finalPredicate = criteriaBuilder.and(monthPredicate, yearPredicate);
//        if(articleFilterDto.getMonth()!=null && articleFilterDto.getYear()!=null){
//            predicates.add(finalPredicate);
//        }
//
//
//        Subquery<Long> likesSubquery = criteriaQuery.subquery(Long.class);
//        Root<Likes> like = likesSubquery.from(Likes.class);
//        likesSubquery.select(criteriaBuilder.coalesce(criteriaBuilder.count(like), 0L))
//                .where(criteriaBuilder.equal(like.get("article"), articleRoot),
//                        criteriaBuilder.equal(like.get("state"), 1));
//
//        Subquery<Long> dislikesSubquery = criteriaQuery.subquery(Long.class);
//        Root<Likes> dislike = dislikesSubquery.from(Likes.class);
//        dislikesSubquery.select(criteriaBuilder.coalesce(criteriaBuilder.count(dislike), 0L))
//                .where(criteriaBuilder.equal(dislike.get("article"), articleRoot),
//                        criteriaBuilder.equal(dislike.get("state"), -1));
//
//        Predicate condition = criteriaBuilder.or(
//                criteriaBuilder.and(
//                        criteriaBuilder.gt(dislikesSubquery, 0L),
//                        criteriaBuilder.gt(criteriaBuilder.prod(criteriaBuilder.quot(likesSubquery, dislikesSubquery), 1.6), 1.0)
//                ),
//                criteriaBuilder.and(
//                        criteriaBuilder.equal(dislikesSubquery, 0L),
//                        criteriaBuilder.gt(likesSubquery, 0L)
//                )
//        );
//
//        if(articleFilterDto.getRating()!=null){
//            predicates.add(condition);
//        }
//        if(joinId!=null){
//            predicates.addAll(getPredicates(filterJoin, articleRoot, joinId, criteriaQuery));
//        }
        criteriaQuery.select(articleRoot)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(criteriaBuilder.desc(articleRoot.get("created")));
        return criteriaQuery;
    }

    public List<Predicate> createJoins(IBusinessLogicFilterSupplier articleFilter, CriteriaQuery<Article> criteriaQuery, Root<Article> articleRoot) {
        List<Predicate> predicates  = new ArrayList<>();
        if(articleFilter.getParam()==-1){
            Root<Subscription> subscription = criteriaQuery.from(Subscription.class);
            Predicate joinCondition = criteriaBuilder.and(
                    criteriaBuilder.equal(subscription.get("author").get("id"), articleRoot.get("author").get("id")),
                    criteriaBuilder.equal(subscription.get("follower").get("id"), id)
            );
            predicates.add(joinCondition);
        }else{

        }


        return new ArrayList<>();
    }
}
