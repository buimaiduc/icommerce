package com.icommerce.app.shopping.product.service.repository;

import org.apache.commons.collections.MapUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class GenericSpesification<T> implements Specification<T> {

    private List<SearchCriteria> searchCriterias;

    public GenericSpesification() {
        this.searchCriterias = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        searchCriterias.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : searchCriterias) {
            if (MapUtils.isNotEmpty(criteria.getKeyValuePair())) {
                List<Predicate> keyValuePairPredicates = new ArrayList<>();
                criteria.getKeyValuePair().forEach((key, value) -> {
                    Predicate predicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(key)),
                            "%" + value.toString().toLowerCase() + "%");
                    keyValuePairPredicates.add(predicate);
                });
                predicates.add(
                        criteriaBuilder.or(keyValuePairPredicates
                                .toArray(new Predicate[keyValuePairPredicates.size()])));
            } else {
                if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                    predicates.add(criteriaBuilder.greaterThan(
                            root.get(criteria.getKey()), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                    predicates.add(criteriaBuilder.lessThan(
                            root.get(criteria.getKey()), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                            root.get(criteria.getKey()), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(
                            root.get(criteria.getKey()), criteria.getValue().toString()));
                } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                    predicates.add(criteriaBuilder.notEqual(
                            root.get(criteria.getKey()), criteria.getValue()));
                } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                    predicates.add(criteriaBuilder.equal(
                            root.get(criteria.getKey()), criteria.getValue()));
                } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(criteria.getKey())),
                            "%" + criteria.getValue().toString().toLowerCase() + "%"));
                } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(criteria.getKey())),
                            criteria.getValue().toString().toLowerCase() + "%"));
                } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                    Expression<String> keyExpression = root.get(criteria.getKey());
                    predicates.add(keyExpression.in(criteria.getValues()));
                } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                    Expression<String> keyExpression = root.get(criteria.getKey());
                    predicates.add(criteriaBuilder.not(keyExpression.in(criteria.getValues())));
                }
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


}
