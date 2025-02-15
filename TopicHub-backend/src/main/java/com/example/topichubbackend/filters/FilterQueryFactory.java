package com.example.topichubbackend.filters;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.dto.filter.*;
import com.example.topichubbackend.model.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

// Числа и строки можно, опять таки, в константы

@Service
public class FilterQueryFactory {

    // Чего не через конструктор внедряем?
    @Autowired
    private CriteriaBuilder criteriaBuilder;

    private Predicate createLikesPredicate(Root<Article> articleRoot, Double rating){
        Double fraction = rating/100;
        Expression<Long> likes = articleRoot.get("likes");
        Expression<Long> dislikes = articleRoot.get("dislikes");
        Expression<Long> total = criteriaBuilder.sum(likes, dislikes);
        Expression<Number> percent = criteriaBuilder.prod(total, fraction);
        return criteriaBuilder.gt(likes, percent);
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

    // Слегка громоздкий метод. Паша говорил, что если на экран помещается, то как бы норм,
    // но я бы подсократил и что-то бы вынес. Остальные меньше меня по объему напрягли
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

        if(articleFilterDto.getAuthorId()!=null
                && articleFilterDto.getUserId()!=null
                &&  articleFilterDto.getAuthorId().equals(articleFilterDto.getUserId())){
            predicates.add(createOwnPredicate(criteriaBuilder, articleRoot, articleFilterDto.getAuthorId()));
        }
        criteriaQuery.select(articleRoot)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(criteriaBuilder.asc(articleRoot.get("created")));
        return criteriaQuery;
    }

    private Predicate createOwnPredicate(CriteriaBuilder criteriaBuilder, Root<Article> articleRoot, String authorId) {
        Join<Article, User> authorJoin = articleRoot.join("author", JoinType.INNER);
        return criteriaBuilder.equal(authorJoin.get("uuid"), UUID.fromString(authorId));
    }

    private Predicate createStatusPredicate(CriteriaBuilder criteriaBuilder, Root<Article> articleRoot, String value) {
        return criteriaBuilder.equal(articleRoot.get("status"), value);
    }

    public List<Predicate> createJoins(IBusinessLogicFilterSupplier articleFilter, CriteriaQuery<Article> criteriaQuery, Root<Article> articleRoot) {
        List<Predicate> predicates  = new ArrayList<>();
        if(articleFilter.getParam() == null){
            return new ArrayList<>();
        }
        if( articleFilter.getParam()==-1 && articleFilter.getUserId()!=null){
            Root<Subscription> subscription = criteriaQuery.from(Subscription.class);
            var id = articleFilter.getUserId();
            Predicate joinCondition = criteriaBuilder.and(
                    criteriaBuilder.equal(subscription.get("author").get("uuid"), articleRoot.get("author").get("uuid")),
                    criteriaBuilder.equal(subscription.get("follower").get("uuid"), UUID.fromString(id))
            );
            predicates.add(joinCondition);
        }else if(articleFilter.getParam()>0){
            Join<Article, Hub> hubJoin = articleRoot.join("hub", JoinType.INNER);
            Predicate hubPredicate = criteriaBuilder.equal(hubJoin.get("id"), articleFilter.getParam());
            predicates.add(hubPredicate);
        }
        return predicates;
    }


}