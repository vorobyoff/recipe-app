package ru.vorobyoff.recipeapp.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCommand {

    private UnitOfMeasureCommand uofCommand;
    private RecipeCommand recipeCommand;
    private String description;
    private BigDecimal amount;
}