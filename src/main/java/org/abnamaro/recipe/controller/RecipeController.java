package org.abnamaro.recipe.controller;

import io.swagger.annotations.ApiOperation;
import org.abnamaro.recipe.entities.Recipe;
import org.abnamaro.recipe.entities.SearchRequestDTO;
import org.abnamaro.recipe.service.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeService;


    @ApiOperation(value = "search for recipes, you can filter using include or exclude ingredients, instruction, if is vegetarian or number of servings ")
    @GetMapping
    public ResponseEntity<List<Recipe>> searchRecipes(SearchRequestDTO searchRequestDTO) {
        try {
            return new ResponseEntity<List<Recipe>>(recipeService.searchRecipes(searchRequestDTO), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<Recipe>>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "insert new recipe ")
    @PostMapping
    public void addRecipe(@RequestBody Recipe recipe) {
        recipeService.saveRecipe(recipe);
    }

    @ApiOperation(value = "update existing recipe ")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@RequestBody Recipe recipe, @PathVariable Integer id) {
        try {
            recipeService.getRecipe(id);
            recipe.setId(id);
            recipeService.saveRecipe(recipe);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @ApiOperation(value = "delete existing recipe")
    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Integer id) {
        recipeService.delete(id);
    }

}
