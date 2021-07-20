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

    private UnitOfMeasureCommand uom;
    private RecipeCommand recipe;
    private String description;
    private BigDecimal amount;

    @Builder
    public IngredientCommand(final Long id, final UnitOfMeasureCommand uom, final RecipeCommand recipe,
                             final String description, final BigDecimal amount) {
        super(id);
        this.uom = uom;
        this.recipe = recipe;
        this.description = description;
        this.amount = amount;
    }
}