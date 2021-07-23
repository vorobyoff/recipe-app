package ru.vorobyoff.recipeapp.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public final class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final Converter<UnitOfMeasureCommand, UnitOfMeasure> uomConverter;

    @Nullable
    @Override
    public Ingredient convert(final IngredientCommand command) {
        if (isNull(command)) return null;

        final var uom = uomConverter.convert(command.getUom());

        return Ingredient.builder()
                .description(command.getDescription())
                .amount(command.getAmount())
                .id(command.getId())
                .uom(uom)
                .build();
    }
}