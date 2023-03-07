package org.abnamaro.recipes.controller;

import io.swagger.annotations.ApiOperation;
import org.abnamaro.recipes.entities.Recipes;
import org.abnamaro.recipes.entities.SearchRequestDTO;
import org.abnamaro.recipes.service.RecipesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/recipes")
public class RecipesController {

    @Autowired
    RecipesService recipesService;


    @ApiOperation(value = "Upload csv file")
    @GetMapping
    public ResponseEntity<List<Recipes>> searchRecipes(SearchRequestDTO searchRequestDTO) {
        try {
            return new ResponseEntity<List<Recipes>>(recipesService.searchRecipes(searchRequestDTO), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<Recipes>>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public void addRecipes(@RequestBody Recipes recipes) {
        recipesService.saveRecipes(recipes);
    }

    @PutMapping("/{{id}")
    public ResponseEntity<?> updateRecipes(@RequestBody Recipes recipes, @PathVariable Integer id) {
        try {
            recipesService.getRecipe(id);
            recipes.setId(id);
            recipesService.saveRecipes(recipes);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void deleteRecipes(@PathVariable Integer id) {
        recipesService.delete(id);
    }

}
