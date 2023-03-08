package org.abnamaro.recipe.service.integrationTest.recipe.builder.recipe;

public class RecipeBuilder {

    private Integer id = 0;
    private boolean isVegetarian;
    private int servings;
    private String ingredients;
    private String instructions;

    private RecipeBuilder() {
    }

    public static RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    public RecipeBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public RecipeBuilder withIsVegetarian(Boolean isVegetarian) {
        this.isVegetarian = isVegetarian;
        return this;
    }

    public RecipeBuilder withServing(Integer servings) {
        this.servings = servings;
        return this;
    }

    public RecipeBuilder withIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public RecipeBuilder withInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public Recipe build() {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setServings(servings);
        recipe.setVegetarian(isVegetarian);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        return recipe;
    }


}
