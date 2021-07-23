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
import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final Converter<Ingredient, IngredientCommand> ingredientConverter;
    private final Converter<Category, CategoryCommand> categoryConverter;
    private final Converter<Note, NoteCommand> notesConverter;

    @Nullable
    @Override
    public RecipeCommand convert(final Recipe source) {
        if (source == null) return null;

        final var ingredientCommands = convertIngredients(source.getIngredients());
        final var categoryCommands = convertCategories(source.getCategories());
        final var noteCommand = notesConverter.convert(source.getNote());

        return RecipeCommand.builder()
                .description(source.getDescription())
                .difficulty(source.getDifficulty())
                .ingredients(ingredientCommands)
                .direction(source.getDirection())
                .cookTime(source.getCookTime())
                .prepTime(source.getPrepTime())
                .categories(categoryCommands)
                .serving(source.getServing())
                .source(source.getSource())
                .url(source.getUrl())
                .note(noteCommand)
                .id(source.getId())
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