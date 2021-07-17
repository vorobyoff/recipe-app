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

public class RecipeCommandToRecipeTest {

    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTION = "Directions";
    public static final Difficulty DIFFICULTY = EASY;
    public static final String URL = "Some URL";
    public static final Integer COOK_TIME = 5;
    public static final Integer PREP_TIME = 7;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Integer SERVINGS = 3;
    public static final Long RECIPE_ID = 1L;
    public static final Long CAT_ID_1 = 1L;
    public static final Long NOTES_ID = 9L;
    public static final Long CAT_ID_2 = 2L;
    public static final Integer SOURCE = 1;

    private RecipeCommandToRecipe converter;

    @BeforeEach
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new CategoryCommandToCategory(),
                new NoteCommandToNote());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert() throws Exception {
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