package org.abnamaro.recipe.service.integrationTest.recipe.builder.recipe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import org.abnamaro.recipe.service.integrationTest.recipe.builder.searchRequest.SearchRequestDTO;

import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class Recipe {
    private Integer id;
    private boolean isVegetarian;
    private int servings;
    private String ingredients;
    private String instructions;

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String result = "";
        try {
            result = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, "fail to serialize Recipe, " + e.getMessage());
        }
        return result;
    }
}
