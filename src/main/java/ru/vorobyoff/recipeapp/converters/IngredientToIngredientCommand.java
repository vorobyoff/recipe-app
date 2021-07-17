package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final Converter<UnitOfMeasure, UnitOfMeasureCommand> uomConverter;

    public IngredientToIngredientCommand(final Converter<UnitOfMeasure, UnitOfMeasureCommand> uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) return null;

        final var uom = uomConverter.convert(ingredient.getUom());

        return IngredientCommand.builder()
                .description(ingredient.getDescription())
                .amount(ingredient.getAmount())
                .id(ingredient.getId())
                .uofCommand(uom)
                .build();
    }
}