package org.abnamaro.recipe.service.integrationTest.recipe.builder.searchRequest;

import org.abnamaro.recipe.entities.RecipeTextSearchEnum;

public class SearchRequestDTOBuilder {

    private String text = null;
    private RecipeTextSearchEnum fields = RecipeTextSearchEnum.All;
    private Boolean isVegetarian = false;
    private Integer servings = 1;
    private Boolean exclude = false;

    private SearchRequestDTOBuilder() {
    }

    public static SearchRequestDTOBuilder searchRequestDTO() {
        return new SearchRequestDTOBuilder();
    }

    public SearchRequestDTOBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public SearchRequestDTOBuilder withRecipeTextSearchEnum(RecipeTextSearchEnum fields) {
        this.fields = fields;
        return this;
    }

    public SearchRequestDTOBuilder withIsVegetarian(Boolean isVegetarian) {
        this.isVegetarian = isVegetarian;
        return this;
    }

    public SearchRequestDTOBuilder withServing(Integer servings) {
        this.servings = servings;
        return this;
    }

    public SearchRequestDTOBuilder withExclude(Boolean exclude) {
        this.exclude = exclude;
        return this;
    }

    public SearchRequestDTO build() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setExclude(exclude);
        searchRequestDTO.setFields(fields);
        searchRequestDTO.setServings(servings);
        searchRequestDTO.setIsVegetarian(isVegetarian);
        searchRequestDTO.setText(text);

        return searchRequestDTO;
    }

}
