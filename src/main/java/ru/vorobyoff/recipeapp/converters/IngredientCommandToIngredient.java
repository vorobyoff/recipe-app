package ru.vorobyoff.recipeapp.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

@Component
@RequiredArgsConstructor
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final Converter<UnitOfMeasureCommand, UnitOfMeasure> uomConverter;

    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) return null;

        final var uom = uomConverter.convert(source.getUofCommand());

        return Ingredient.builder()
                .description(source.getDescription())
                .amount(source.getAmount())
                .id(source.getId())
                .uom(uom)
                .build();
    }
}