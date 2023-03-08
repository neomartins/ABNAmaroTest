package org.abnamaro.recipe.service.integrationTest.recipe;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.abnamaro.recipe.service.integrationTest.recipe.builder.recipe.Recipe;
import org.abnamaro.recipe.service.integrationTest.recipe.builder.recipe.RecipeBuilder;
import org.abnamaro.recipe.service.integrationTest.runner.SpringIntegrationTest;
import org.apache.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipesDefinitions extends SpringIntegrationTest {

    private Response response;
    private JsonPath jsonPath;

    @When("i want get all recipe")
    public void iWantToGetAllRecipe() {
        response = given().when()
                .header("Content-Type", ContentType.JSON)
                .when()
                .get(DEFAULT_URL + "recipe");
        assertEquals(HttpStatus.SC_OK, response.statusCode());
    }

    @When("i want to get all vegetarian recipe")
    public void iWantToGetAllVegetarianRecipe() {
        response = given().when()
                .header("Content-Type", ContentType.JSON)
                .when()
                .get(DEFAULT_URL + "recipe" + "?isVegetarian=true");
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        jsonPath = response.jsonPath();
    }

    @Then("i have only vegetarian recipe")
    public void iHaveOnlyVegetarianRecipe() {
        List<Recipe> recipeList = jsonPath.getList("$", Recipe.class);
        assertTrue(recipeList.stream().allMatch(Recipe::isVegetarian));
    }

    @When("i want to get all recipe that do not have rice")
    public void iWantToGetAllRecipeThatDoNotHaveRice() {
        response = given().when()
                .header("Content-Type", ContentType.JSON)
                .when()
                .get(DEFAULT_URL + "recipe" + "?exclude=true&text=rice oven");
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        jsonPath = response.jsonPath();
    }

    @Then("i have only recipe without rice")
    public void iHaveOnlyRecipeWithoutRice() {
        List<Recipe> recipeList = jsonPath.getList("$", Recipe.class);
        assertTrue(recipeList.stream().noneMatch(recipe -> recipe.getIngredients().contains("rice")));
    }

    @When("i want to get all recipe that have rice and is made on oven")
    public void iWantToGetAllRecipeThatHaveRiceAndIsMadeOnOven() {
        response = given().when()
                .header("Content-Type", ContentType.JSON)
                .when()
                .get(DEFAULT_URL + "recipe" + "?text=oven rice");
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        jsonPath = response.jsonPath();
    }

    @Then("i have only recipe with rice and made on oven")
    public void iHaveOnlyRecipeWithRiceAndMadeOnOven() {
        List<Recipe> recipeList = jsonPath.getList("$", Recipe.class);
        assertTrue(recipeList.stream().allMatch(recipe -> recipe.getIngredients().contains("rice") || recipe.getInstructions().contains("oven")));
    }

    @When("i want to update a new recipe")
    public void iWantToUpdateNewRecipe() {
        Recipe recipe = RecipeBuilder
                .recipeBuilder()
                .withIngredients("test, testes, tes")
                .withInstructions("oven")
                .build();
        response = with()
                .header("Content-Type", ContentType.JSON)
                .when()
                .body(recipe)
                .put(DEFAULT_URL + "recipe/{id}", 1).prettyPeek();

        assertEquals(HttpStatus.SC_OK, response.statusCode());
    }

    @Then("i get the updated recipe")
    public void iGetTheUpdatedRecipe() {
        response = given()
                .header("Content-Type", ContentType.JSON)
                .when()
                .get(DEFAULT_URL + "recipe" + "?fields=INGREDIENTS&text=test, testes").prettyPeek();
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        jsonPath = response.jsonPath();
        assertTrue(jsonPath.getString("ingredients").contains("test, testes, tes"));
    }

    @When("i want to insert a new recipe")
    public void iWantToInsertNewRecipe() {
        Recipe recipe = RecipeBuilder
                .recipeBuilder()
                .withIngredients("test, testes, tes")
                .withInstructions("oven")
                .withIsVegetarian(true)
                .build();
        response = with()
                .header("Content-Type", ContentType.JSON)
                .when()
                .body(recipe)
                .post(DEFAULT_URL + "recipe").prettyPeek();
    }

    @When("i want to delete a new recipe")
    public void iWantToDeleteNewRecipe() {
        response = with()
                .header("Content-Type", ContentType.JSON)
                .when()
                .delete(DEFAULT_URL + "recipe/{id}", 1);
    }

    @Then("the status code is 200")
    public void theStatusCodeIs200() {
        assertEquals(HttpStatus.SC_OK, response.statusCode());
    }
}
