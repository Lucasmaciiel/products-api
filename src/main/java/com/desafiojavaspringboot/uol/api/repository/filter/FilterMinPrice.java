package com.desafiojavaspringboot.uol.api.repository.filter;

import com.desafiojavaspringboot.uol.api.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
@RequiredArgsConstructor
public class FilterMinPrice implements Specification<Product> {

    private final Double min_price;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (min_price == null){
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        else {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), this.min_price.toString());
        }
    }
}
