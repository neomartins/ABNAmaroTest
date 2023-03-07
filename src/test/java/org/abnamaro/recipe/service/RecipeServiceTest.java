package org.abnamaro.recipe.service;

import org.abnamaro.recipe.entities.Recipe;
import org.abnamaro.recipe.entities.RecipeTextSearchEnum;
import org.abnamaro.recipe.entities.SearchRequestDTO;
import org.abnamaro.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RecipeServiceTest {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        Recipe recipe1 = new Recipe();
        recipe1.setId(1);
        recipe1.setIngredients("egg, potato, rice, chicken");
        recipe1.setInstructions("oven");
        recipe1.setServings(2);
        recipe1.setVegetarian(true);

        Recipe recipe2 = new Recipe();
        recipe2.setId(2);
        recipe2.setIngredients("egg, potato, rice, meat");
        recipe2.setInstructions("oven");
        recipe2.setServings(4);
        recipe2.setVegetarian(false);

        Recipe recipe3 = new Recipe();
        recipe3.setId(3);
        recipe3.setIngredients("egg, potato, rice, salmon");
        recipe3.setInstructions("air fryer");
        recipe3.setServings(3);
        recipe3.setVegetarian(true);

        Recipe recipe4 = new Recipe();
        recipe4.setId(4);
        recipe4.setIngredients("egg, potato, rice, meat");
        recipe4.setInstructions("cooked");
        recipe4.setServings(3);
        recipe4.setVegetarian(false);

        Recipe recipe5 = new Recipe();
        recipe5.setId(5);
        recipe5.setIngredients("potato, fish");
        recipe5.setInstructions("air fryer");
        recipe5.setServings(5);
        recipe5.setVegetarian(false);

        recipeRepository.saveAll(Arrays.asList(recipe1, recipe2, recipe3, recipe4, recipe5));
    }

    @AfterEach
    void tearDown() {
        recipeRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return all recipes when the text is empty")
    void searchRecipesWhenTextIsEmptyThenReturnAllRecipes() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setText("");
        List<Recipe> recipeList = recipeService.searchRecipes(searchRequestDTO);
        assertEquals(5, recipeList.size());
    }

    @Test
    @DisplayName("Should return all recipes when the text is blank")
    void searchRecipesWhenTextIsBlankThenReturnAllRecipes() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setText(" ");
        List<Recipe> recipeList = recipeService.searchRecipes(searchRequestDTO);
        assertEquals(5, recipeList.size());
    }

    @Test
    @DisplayName("Should return all recipes when the text is null")
    void searchRecipesWhenTextIsNullThenReturnAllRecipes() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setText(null);

        List<Recipe> recipeList = recipeService.searchRecipes(searchRequestDTO);

        assertEquals(5, recipeList.size());
    }


    @Test
    @DisplayName(
            "Should return recipes that contain the text in ingredients field when the fields is null and exclude is null or false")
    void
    searchRecipesWhenFieldsIsNullAndExcludeIsNullOrFalseThenReturnRecipeThatContainTheTextInIngredientsField() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setText("egg");
        searchRequestDTO.setFields(null);
        searchRequestDTO.setExclude(null);

        List<Recipe> recipeList = recipeService.searchRecipes(searchRequestDTO);

        assertEquals(4, recipeList.size());
    }

    @Test
    @DisplayName(
            "Should return recipes that contain the text in ingredients field when the fields equals to all and exclude is null or false")
    void
    searchRecipesWhenFieldsEqualsToAllAndExcludeIsNullOrFalseThenReturnRecipeThatContainTheTextInIngredientsField() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setText("egg");
        searchRequestDTO.setFields(RecipeTextSearchEnum.All);
        searchRequestDTO.setExclude(null);

        List<Recipe> recipeList = recipeService.searchRecipes(searchRequestDTO);

        assertEquals(4, recipeList.size());
    }

    @Test
    @DisplayName(
            "Should return recipes that is vegetarian")
    void
    searchRecipesWhenFieldsIsVegetarianIsTrue() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setIsVegetarian(true);
        List<Recipe> recipeList = recipeService.searchRecipes(searchRequestDTO);
        assertEquals(2, recipeList.size());
    }

    @Test
    @DisplayName(
            "Should return all recipes that don`t have egg")
    void
    searchAllRecipesWhenExcludeTrueEgg() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setExclude(true);
        searchRequestDTO.setText("egg air fryer");
        List<Recipe> recipeList = recipeService.searchRecipes(searchRequestDTO);
        assertEquals(1, recipeList.size());
    }

    @Test
    @DisplayName(
            "Should return all recipes that has oven")
    void
    searchAllRecipesWhenInstructionHasOven() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setText("oven");
        List<Recipe> recipeList = recipeService.searchRecipes(searchRequestDTO);
        assertEquals(2, recipeList.size());
    }

    @Test
    @DisplayName(
            "Should return all recipes that has oven and exclude meat")
    void
    searchAllRecipesWhenInstructionHasOvenExcludeMeat() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setExclude(true);
        searchRequestDTO.setText("oven chicken");
        List<Recipe> recipeList = recipeService.searchRecipes(searchRequestDTO);
        assertEquals(1, recipeList.size());
    }

    @Test
    @DisplayName(
            "Should return all recipes that has oven and exclude meat serving 5 people")
    void
    searchAllRecipesWhenInstructionHasOvenExcludeMeatServingFivePeople() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setExclude(true);
        searchRequestDTO.setText("oven chicken");
        searchRequestDTO.setServings(5);
        List<Recipe> recipeList = recipeService.searchRecipes(searchRequestDTO);
        assertEquals(0, recipeList.size());
    }
}