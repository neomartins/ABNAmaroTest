package org.abnamaro.recipes.service;

import org.abnamaro.recipes.RecipesEnumTextSearch.RecipesTextSearchEnum;
import org.abnamaro.recipes.entities.Recipes;
import org.abnamaro.recipes.entities.SearchRequestDTO;
import org.abnamaro.recipes.repository.RecipesRepository;
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
class RecipesServiceTest {

    @Autowired
    private RecipesService recipesService;

    @Autowired
    private RecipesRepository recipesRepository;

    @BeforeEach
    void setUp() {
        Recipes recipes1 = new Recipes();
        recipes1.setId(1);
        recipes1.setIngredients("egg, potato, rice, chicken");
        recipes1.setInstructions("oven");
        recipes1.setServings(2);
        recipes1.setVegetarian(true);

        Recipes recipes2 = new Recipes();
        recipes2.setId(2);
        recipes2.setIngredients("egg, potato, rice, meat");
        recipes2.setInstructions("oven");
        recipes2.setServings(4);
        recipes2.setVegetarian(false);

        Recipes recipes3 = new Recipes();
        recipes3.setId(3);
        recipes3.setIngredients("egg, potato, rice, salmon");
        recipes3.setInstructions("air fryer");
        recipes3.setServings(3);
        recipes3.setVegetarian(true);

        Recipes recipes4 = new Recipes();
        recipes4.setId(4);
        recipes4.setIngredients("egg, potato, rice, meat");
        recipes4.setInstructions("cooked");
        recipes4.setServings(3);
        recipes4.setVegetarian(false);

        Recipes recipes5 = new Recipes();
        recipes5.setId(5);
        recipes5.setIngredients("potato, fish");
        recipes5.setInstructions("air fryer");
        recipes5.setServings(5);
        recipes5.setVegetarian(false);

        recipesRepository.saveAll(Arrays.asList(recipes1, recipes2, recipes3, recipes4, recipes5));
    }

    @AfterEach
    void tearDown() {
        recipesRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return all recipes when the text is empty")
    void searchRecipesWhenTextIsEmptyThenReturnAllRecipes() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setText("");
        List<Recipes> recipesList = recipesService.searchRecipes(searchRequestDTO);
        assertEquals(5, recipesList.size());
    }

    @Test
    @DisplayName("Should return all recipes when the text is blank")
    void searchRecipesWhenTextIsBlankThenReturnAllRecipes() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setText(" ");
        List<Recipes> recipesList = recipesService.searchRecipes(searchRequestDTO);
        assertEquals(5, recipesList.size());
    }

    @Test
    @DisplayName("Should return all recipes when the text is null")
    void searchRecipesWhenTextIsNullThenReturnAllRecipes() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setText(null);

        List<Recipes> recipesList = recipesService.searchRecipes(searchRequestDTO);

        assertEquals(5, recipesList.size());
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

        List<Recipes> recipesList = recipesService.searchRecipes(searchRequestDTO);

        assertEquals(4, recipesList.size());
    }

    @Test
    @DisplayName(
            "Should return recipes that contain the text in ingredients field when the fields equals to all and exclude is null or false")
    void
    searchRecipesWhenFieldsEqualsToAllAndExcludeIsNullOrFalseThenReturnRecipeThatContainTheTextInIngredientsField() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setText("egg");
        searchRequestDTO.setFields(RecipesTextSearchEnum.All);
        searchRequestDTO.setExclude(null);

        List<Recipes> recipesList = recipesService.searchRecipes(searchRequestDTO);

        assertEquals(4, recipesList.size());
    }

    @Test
    @DisplayName(
            "Should return recipes that is vegetarian")
    void
    searchRecipesWhenFieldsIsVegetarianIsTrue() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setIsVegetarian(true);
        List<Recipes> recipesList = recipesService.searchRecipes(searchRequestDTO);
        assertEquals(2, recipesList.size());
    }

    @Test
    @DisplayName(
            "Should return all recipes that don`t have egg")
    void
    searchAllRecipesWhenExcludeTrueEgg() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setExclude(true);
        searchRequestDTO.setText("egg air fryer");
        List<Recipes> recipesList = recipesService.searchRecipes(searchRequestDTO);
        assertEquals(1, recipesList.size());
    }

    @Test
    @DisplayName(
            "Should return all recipes that has oven")
    void
    searchAllRecipesWhenInstructionHasOven() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setText("oven");
        List<Recipes> recipesList = recipesService.searchRecipes(searchRequestDTO);
        assertEquals(2, recipesList.size());
    }

    @Test
    @DisplayName(
            "Should return all recipes that has oven and exclude meat")
    void
    searchAllRecipesWhenInstructionHasOvenExcludeMeat() {
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setExclude(true);
        searchRequestDTO.setText("oven chicken");
        List<Recipes> recipesList = recipesService.searchRecipes(searchRequestDTO);
        assertEquals(1, recipesList.size());
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
        List<Recipes> recipesList = recipesService.searchRecipes(searchRequestDTO);
        assertEquals(0, recipesList.size());
    }
}