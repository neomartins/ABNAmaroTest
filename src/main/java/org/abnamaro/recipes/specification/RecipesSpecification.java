package org.abnamaro.recipes.specification;

import org.abnamaro.recipes.entities.Recipes;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecipesSpecification {

    public Specification<Recipes> getRecipes(Integer servings, Boolean isVegetarian) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (servings != null) {
                predicates.add(criteriaBuilder.equal(root.get("servings"), servings));
            }
            if (isVegetarian != null) {
                predicates.add(criteriaBuilder.equal(root.get("isVegetarian"), isVegetarian));
            }
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
