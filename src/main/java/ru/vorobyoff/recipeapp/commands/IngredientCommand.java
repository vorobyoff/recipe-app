package ru.vorobyoff.recipeapp.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand extends BaseCommand {

    private UnitOfMeasureCommand uofCommand;
    private RecipeCommand recipeCommand;
    private String description;
    private BigDecimal amount;

    @Builder
    public IngredientCommand(final Long id, final UnitOfMeasureCommand uofCommand, final RecipeCommand recipeCommand, final String description, final BigDecimal amount) {
        super(id);
        this.uofCommand = uofCommand;
        this.recipeCommand = recipeCommand;
        this.description = description;
        this.amount = amount;
    }
}