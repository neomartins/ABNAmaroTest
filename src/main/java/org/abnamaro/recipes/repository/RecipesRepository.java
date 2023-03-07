package org.abnamaro.recipes.repository;

import org.abnamaro.recipes.entities.Recipes;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  RecipesRepository extends SearchRepository<Recipes, Integer>, JpaSpecificationExecutor<Recipes> {
}
