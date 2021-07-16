package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
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

import static java.util.stream.Collectors.toSet;

@Component
public final class RecipeCommandToRecipeConverter implements Converter<RecipeCommand, Recipe> {

    private final Converter<IngredientCommand, Ingredient> ingredientConverter;
    private final Converter<CategoryCommand, Category> categoryConverter;
    private final Converter<NoteCommand, Note> noteConverter;

    public RecipeCommandToRecipeConverter(final Converter<IngredientCommand, Ingredient> ingredientConverter,
                                          final Converter<CategoryCommand, Category> categoryConverter,
                                          final Converter<NoteCommand, Note> noteConverter) {
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
        this.noteConverter = noteConverter;
    }

    @Override
    public Recipe convert(final RecipeCommand source) {
        if (source == null) return null;

        final var ingredient = convertIngredients(source.getIngredients());
        final var categories = convertCategories(source.getCategories());
        final var note = noteConverter.convert(source.getNote());

        return Recipe.builder()
                .description(source.getDescription())
                .difficulty(source.getDifficulty())
                .direction(source.getDirection())
                .prepTime(source.getPrepTime())
                .cookTime(source.getCookTime())
                .serving(source.getServing())
                .ingredients(ingredient)
                .categories(categories)
                .image(source.getImage())
                .url(source.getUrl())
                .note(note)
                .build();
    }

    private Set<Ingredient> convertIngredients(final Set<IngredientCommand> commands) {
        return commands.stream().map(ingredientConverter::convert).collect(toSet());
    }

    private Set<Category> convertCategories(final Set<CategoryCommand> commands) {
        return commands.stream().map(categoryConverter::convert).collect(toSet());
    }
}