package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

@Component
public final class IngredientCommandToIngredientConverter implements Converter<IngredientCommand, Ingredient> {

    private final Converter<UnitOfMeasureCommand, UnitOfMeasure> unitOfMeasureConverter;
    private final Converter<RecipeCommand, Recipe> recipeConverter;

    public IngredientCommandToIngredientConverter(final Converter<UnitOfMeasureCommand, UnitOfMeasure> unitOfMeasureConverter,
                                                  final Converter<RecipeCommand, Recipe> recipeConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
        this.recipeConverter = recipeConverter;
    }

    @Override
    public Ingredient convert(final IngredientCommand source) {
        if (source == null) return null;

        final var uom = unitOfMeasureConverter.convert(source.getUofCommand());
        final var recipe = recipeConverter.convert(source.getRecipeCommand());

        return Ingredient.builder()
                .description(source.getDescription())
                .amount(source.getAmount())
                .recipe(recipe)
                .uom(uom)
                .build();
    }
}