package ru.vorobyoff.recipeapp.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.CategoryCommand;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.commands.NoteCommand;
import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.domain.Category;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.Note;
import ru.vorobyoff.recipeapp.domain.Recipe;

import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
public final class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final Converter<Ingredient, IngredientCommand> ingredientConverter;
    private final Converter<Category, CategoryCommand> categoryConverter;
    private final Converter<Note, NoteCommand> notesConverter;

    @Nullable
    @Override
    public RecipeCommand convert(final Recipe recipe) {
        if (isNull(recipe)) return null;

        final var ingredientCommands = convertIngredients(recipe.getIngredients());
        final var categoryCommands = convertCategories(recipe.getCategories());
        final var noteCommand = notesConverter.convert(recipe.getNote());

        return RecipeCommand.builder()
                .description(recipe.getDescription())
                .difficulty(recipe.getDifficulty())
                .ingredients(ingredientCommands)
                .direction(recipe.getDirection())
                .cookTime(recipe.getCookTime())
                .prepTime(recipe.getPrepTime())
                .categories(categoryCommands)
                .serving(recipe.getServing())
                .source(recipe.getSource())
                .image(recipe.getImage())
                .url(recipe.getUrl())
                .note(noteCommand)
                .id(recipe.getId())
                .build();
    }

    private Set<CategoryCommand> convertCategories(final Set<Category> categories) {
        if (categories == null || categories.isEmpty()) return emptySet();
        return categories.stream().map(categoryConverter::convert).collect(toSet());
    }

    private Set<IngredientCommand> convertIngredients(final Set<Ingredient> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) return emptySet();
        return ingredients.stream().map(ingredientConverter::convert).collect(toSet());
    }
}