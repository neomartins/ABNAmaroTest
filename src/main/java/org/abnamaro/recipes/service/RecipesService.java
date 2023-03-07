package org.abnamaro.recipes.service;

import org.abnamaro.recipes.RecipesEnumTextSearch.RecipesTextSearchEnum;
import org.abnamaro.recipes.entities.Recipes;
import org.abnamaro.recipes.entities.SearchRequestDTO;
import org.abnamaro.recipes.repository.RecipesRepository;
import org.abnamaro.recipes.specification.RecipesSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class RecipesService {

    @Autowired
    RecipesRepository recipesRepository;

    @Autowired
    RecipesSpecification recipesSpecification;

    private static final List<String> SEARCHABLE_FIELDS = Arrays.asList("ingredients","instructions");
    private static final List<String> EXCLUDE_SEARCHABLE_FIELDS = Arrays.asList("ingredients");


    public List<Recipes> searchRecipes(SearchRequestDTO searchRequestDTO) {

        List<String> fieldsToSearchBy = searchRequestDTO.getFields() == null || searchRequestDTO.getFields().equals(RecipesTextSearchEnum.All) ?
                SEARCHABLE_FIELDS : Arrays.asList(searchRequestDTO.getFields().toString().toLowerCase(Locale.ROOT));

        if (StringUtils.isBlank(searchRequestDTO.getText())) {{
            return recipesRepository.findAll(recipesSpecification.getRecipes(searchRequestDTO.getServings(), searchRequestDTO.getIsVegetarian()));
        }}

        List<Recipes> recipesList;
        if (searchRequestDTO.getExclude() == null || !searchRequestDTO.getExclude()){
           recipesList  = recipesRepository.searchByContainsText(searchRequestDTO.getText(), fieldsToSearchBy.toArray(new String[0]));
        } else {
            recipesList = recipesRepository.searchByNotContainsText(searchRequestDTO.getText(), EXCLUDE_SEARCHABLE_FIELDS.toArray(new String[0]) , fieldsToSearchBy.toArray(new String[0]));
        }

        return filterRecipesList(recipesList, searchRequestDTO);
    }

    private List<Recipes> filterRecipesList (List<Recipes> recipesList, SearchRequestDTO searchRequestDTO) {
        if(searchRequestDTO.getIsVegetarian() != null && searchRequestDTO.getServings() != null) {
            return recipesList.stream().filter(recipes -> recipes.isVegetarian() == searchRequestDTO.getIsVegetarian()  && recipes.getServings() == searchRequestDTO.getServings()).collect(Collectors.toList());
        } else if (searchRequestDTO.getIsVegetarian() != null) {
            return recipesList.stream().filter(recipes -> recipes.isVegetarian() == searchRequestDTO.getIsVegetarian()).collect(Collectors.toList());
        } else if (searchRequestDTO.getServings() != null){
            return recipesList.stream().filter(recipes -> recipes.getServings() == searchRequestDTO.getServings()).collect(Collectors.toList());
        }
        return recipesList;
    }

    public void saveRecipes(Recipes recipes) {
        recipesRepository.save(recipes);
    }

    public Recipes getRecipe(Integer id) {
        return recipesRepository.getById(id);
    }

    public void delete(Integer id) {
       recipesRepository.deleteById(id);
    }


}
