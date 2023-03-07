package org.abnamaro.recipe.entities;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class SearchRequestDTO {

    @ApiParam(value = "text field for searching, you can search by ingredients, instruction or both")
    private String text;

    private RecipeTextSearchEnum fields;

    @ApiParam(value = "if is vegetarian")
    private Boolean isVegetarian;

    private Integer servings;

    @ApiParam(value = "exclude ingredients")
    private Boolean exclude;
}
