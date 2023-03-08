package org.abnamaro.recipe.service.integrationTest.recipe.builder.searchRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import org.abnamaro.recipe.entities.RecipeTextSearchEnum;

import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class SearchRequestDTO {
    private String text;
    private RecipeTextSearchEnum fields;
    private Boolean isVegetarian;
    private Integer servings;
    private Boolean exclude;

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String result = "";
        try {
            result = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            Logger.getLogger(SearchRequestDTO.class.getName()).log(Level.SEVERE, "fail to serialize SearchRequestDTO, " + e.getMessage());
        }
        return result;
    }
}
