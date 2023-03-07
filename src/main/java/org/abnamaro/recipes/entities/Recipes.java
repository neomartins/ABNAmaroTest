package org.abnamaro.recipes.entities;

import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;

@Indexed
@Entity
@Table(name = "recipes")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Recipes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private boolean isVegetarian;
    private int servings;
    @FullTextField
    private String ingredients;
    @FullTextField
    private String instructions;

    public Recipes(boolean isVegetarian, int servings, String ingredients, String instructions) {
        this.isVegetarian = isVegetarian;
        this.servings = servings;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
}
