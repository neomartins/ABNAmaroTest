package org.abnamaro.recipe.service;

import org.abnamaro.recipe.entities.Recipe;
import org.abnamaro.recipe.entities.RecipeTextSearchEnum;
import org.abnamaro.recipe.entities.SearchRequestDTO;
import org.abnamaro.recipe.repository.RecipeRepository;
import org.abnamaro.recipe.specification.RecipeSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeSpecification recipeSpecification;

    private static final List<String> SEARCHABLE_FIELDS = Arrays.asList("ingredients","instructions");
    private static final List<String> EXCLUDE_SEARCHABLE_FIELDS = Arrays.asList("ingredients");


    public List<Recipe> searchRecipes(SearchRequestDTO searchRequestDTO) {

        List<String> fieldsToSearchBy = searchRequestDTO.getFields() == null || searchRequestDTO.getFields().equals(RecipeTextSearchEnum.All) ?
                SEARCHABLE_FIELDS : Arrays.asList(searchRequestDTO.getFields().toString().toLowerCase(Locale.ROOT));

        if (StringUtils.isBlank(searchRequestDTO.getText())) {{
            return recipeRepository.findAll(recipeSpecification.getRecipe(searchRequestDTO.getServings(), searchRequestDTO.getIsVegetarian()));
        }}

        List<Recipe> recipeList;
        if (searchRequestDTO.getExclude() == null || !searchRequestDTO.getExclude()){
           recipeList = recipeRepository.searchByContainsText(searchRequestDTO.getText(), fieldsToSearchBy.toArray(new String[0]));
        } else {
            recipeList = recipeRepository.searchByNotContainsText(searchRequestDTO.getText(), EXCLUDE_SEARCHABLE_FIELDS.toArray(new String[0]) , fieldsToSearchBy.toArray(new String[0]));
        }

        return filterRecipesList(recipeList, searchRequestDTO);
    }

    private List<Recipe> filterRecipesList (List<Recipe> recipeList, SearchRequestDTO searchRequestDTO) {
        if(searchRequestDTO.getIsVegetarian() != null && searchRequestDTO.getServings() != null) {
            return recipeList.stream().filter(recipe -> recipe.isVegetarian() == searchRequestDTO.getIsVegetarian()  && recipe.getServings() == searchRequestDTO.getServings()).collect(Collectors.toList());
        } else if (searchRequestDTO.getIsVegetarian() != null) {
            return recipeList.stream().filter(recipe -> recipe.isVegetarian() == searchRequestDTO.getIsVegetarian()).collect(Collectors.toList());
        } else if (searchRequestDTO.getServings() != null){
            return recipeList.stream().filter(recipe -> recipe.getServings() == searchRequestDTO.getServings()).collect(Collectors.toList());
        }
        return recipeList;
    }

    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public Recipe getRecipe(Integer id) {
        return recipeRepository.getById(id);
    }

    public void delete(Integer id) {
       recipeRepository.deleteById(id);
    }


}
