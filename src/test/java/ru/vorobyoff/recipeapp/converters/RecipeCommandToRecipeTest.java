package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.commands.CategoryCommand;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.commands.NoteCommand;
import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.domain.Difficulty;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ru.vorobyoff.recipeapp.domain.Difficulty.EASY;

final class RecipeCommandToRecipeTest {

    private static final String DESCRIPTION = "My Recipe";
    private static final String DIRECTION = "Directions";
    private static final Difficulty DIFFICULTY = EASY;
    private static final String URL = "Some URL";
    private static final Integer COOK_TIME = 5;
    private static final Integer PREP_TIME = 7;
    private static final Long INGRED_ID_1 = 3L;
    private static final Long INGRED_ID_2 = 4L;
    private static final Integer SERVINGS = 3;
    private static final Long RECIPE_ID = 1L;
    private static final Long CAT_ID_1 = 1L;
    private static final Long NOTES_ID = 9L;
    private static final Long CAT_ID_2 = 2L;
    private static final Integer SOURCE = 1;

    private RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new CategoryCommandToCategory(),
                new NoteCommandToNote());
    }

    @Test
    void convertNullPassedCase() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObjectPassedCase() {
        assertNotNull(converter.convert(RecipeCommand.builder().build()));
    }

    @Test
    void convert() throws Exception {
        final var note = NoteCommand.builder().id(NOTES_ID).build();
        final var firstCategory = CategoryCommand.builder().id(CAT_ID_1).build();
        final var secondCategory = CategoryCommand.builder().id(CAT_ID_2).build();
        final var firstIngredient = IngredientCommand.builder().id(INGRED_ID_1).build();
        final var secondIngredient = IngredientCommand.builder().id(INGRED_ID_2).build();

        final var recipeCommand = RecipeCommand.builder()
                .ingredients(Set.of(firstIngredient, secondIngredient))
                .categories(Set.of(firstCategory, secondCategory))
                .description(DESCRIPTION)
                .difficulty(DIFFICULTY)
                .direction(DIRECTION)
                .cookTime(COOK_TIME)
                .prepTime(PREP_TIME)
                .serving(SERVINGS)
                .source(SOURCE)
                .id(RECIPE_ID)
                .note(note)
                .url(URL)
                .build();

        final var recipe = converter.convert(recipeCommand);
        assertNotNull(recipe);

        assertEquals(URL, recipe.getUrl());
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(SERVINGS, recipe.getServing());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DIRECTION, recipe.getDirection());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(NOTES_ID, recipe.getNote().getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
    }
}