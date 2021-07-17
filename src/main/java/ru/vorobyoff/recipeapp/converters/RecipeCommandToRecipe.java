package ru.vorobyoff.recipeapp.converters;

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
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final Converter<IngredientCommand, Ingredient> ingredientConverter;
    private final Converter<CategoryCommand, Category> categoryConverter;
    private final Converter<NoteCommand, Note> notesConverter;

    public RecipeCommandToRecipe(final Converter<IngredientCommand, Ingredient> ingredientConverter,
                                 final Converter<CategoryCommand, Category> categoryConverter,
                                 final Converter<NoteCommand, Note> notesConverter) {
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
        this.notesConverter = notesConverter;
    }

    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) return null;

        final var ingredients = convertIngredients(source.getIngredients());
        final var categories = convertCategories(source.getCategories());
        final var note = notesConverter.convert(source.getNote());

        return Recipe.builder()
                .description(source.getDescription())
                .difficulty(source.getDifficulty())
                .direction(source.getDirection())
                .cookTime(source.getCookTime())
                .prepTime(source.getPrepTime())
                .serving(source.getServing())
                .ingredients(ingredients)
                .source(source.getSource())
                .categories(categories)
                .url(source.getUrl())
                .id(source.getId())
                .note(note)
                .build();
    }

    private Set<Category> convertCategories(final Set<CategoryCommand> commands) {
        if (commands == null || commands.isEmpty()) return emptySet();
        return commands.stream().map(categoryConverter::convert).collect(toSet());
    }

    private Set<Ingredient> convertIngredients(final Set<IngredientCommand> commands) {
        if (commands == null || commands.isEmpty()) return emptySet();
        return commands.stream().map(ingredientConverter::convert).collect(toSet());
    }
}