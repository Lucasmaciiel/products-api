package com.desafiojavaspringboot.uol.api.repository.filter;

import com.desafiojavaspringboot.uol.api.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
public class FilterNameDescription implements Specification<Product> {

    private final String q;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (q == null){
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        else {
            return criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + this.q + "%"),
                    criteriaBuilder.like(root.get("description"), "%" + this.q + "%"));
        }
    }
}
