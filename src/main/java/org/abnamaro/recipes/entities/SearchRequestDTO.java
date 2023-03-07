package org.abnamaro.recipes.entities;

import lombok.Data;
import org.abnamaro.recipes.RecipesEnumTextSearch.RecipesTextSearchEnum;

@Data
public class SearchRequestDTO {

    private String text;

    private RecipesTextSearchEnum fields;

    private Boolean isVegetarian;

    private Integer servings;

    private Boolean exclude;
}
