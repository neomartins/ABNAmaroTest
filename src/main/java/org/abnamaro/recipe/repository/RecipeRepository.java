package org.abnamaro.recipe.repository;

import org.abnamaro.recipe.entities.Recipe;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends SearchRepository<Recipe, Integer>, JpaSpecificationExecutor<Recipe> {
}
