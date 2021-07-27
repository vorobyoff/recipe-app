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
public final class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final Converter<IngredientCommand, Ingredient> ingredientConverter;
    private final Converter<CategoryCommand, Category> categoryConverter;
    private final Converter<NoteCommand, Note> notesConverter;

    @Nullable
    @Override
    public Recipe convert(final RecipeCommand command) {
        if (isNull(command)) return null;

        final var ingredients = convertIngredients(command.getIngredients());
        final var categories = convertCategories(command.getCategories());
        final var note = notesConverter.convert(command.getNote());

        final var recipe = Recipe.builder()
                .description(command.getDescription())
                .difficulty(command.getDifficulty())
                .direction(command.getDirection())
                .cookTime(command.getCookTime())
                .prepTime(command.getPrepTime())
                .serving(command.getServing())
                .ingredients(ingredients)
                .source(command.getSource())
                .categories(categories)
                .image(command.getImage())
                .url(command.getUrl())
                .id(command.getId())
                .note(note)
                .build();

        setupIngredientsRecipe(ingredients, recipe);

        return recipe;
    }

    private Set<Category> convertCategories(final Set<CategoryCommand> commands) {
        if (commands == null || commands.isEmpty()) return emptySet();
        return commands.stream().map(categoryConverter::convert).collect(toSet());
    }

    private Set<Ingredient> convertIngredients(final Set<IngredientCommand> commands) {
        if (commands == null || commands.isEmpty()) return emptySet();
        return commands.stream().map(ingredientConverter::convert).collect(toSet());
    }

    private void setupIngredientsRecipe(final Set<Ingredient> ingredients, final Recipe recipe) {
        if (ingredients.isEmpty()) return;
        ingredients.forEach(ingredient -> ingredient.setRecipe(recipe));
    }
}