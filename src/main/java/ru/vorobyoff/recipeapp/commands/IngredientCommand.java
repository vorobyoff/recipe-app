package ru.vorobyoff.recipeapp.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public final class IngredientCommand extends BaseCommand {

    private UnitOfMeasureCommand uom;
    private Long recipeId;
    private String description;
    private BigDecimal amount;

    @Builder
    public IngredientCommand(final Long id, final UnitOfMeasureCommand uom, final Long recipeId,
                             final String description, final BigDecimal amount) {
        super(id);
        this.uom = uom;
        this.recipeId = recipeId;
        this.description = description;
        this.amount = amount;
    }
}