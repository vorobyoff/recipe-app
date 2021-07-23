package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.domain.Category;
import ru.vorobyoff.recipeapp.domain.Difficulty;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.Note;
import ru.vorobyoff.recipeapp.domain.Recipe;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ru.vorobyoff.recipeapp.domain.Difficulty.EASY;

final class RecipeToRecipeCommandTest {

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

    private RecipeToRecipeCommand converter;

    @BeforeEach
    void setUp() throws Exception {
        converter = new RecipeToRecipeCommand(
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand(),
                new NoteToNoteCommand());
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(Recipe.builder().build()));
    }

    @Test
    void convert() throws Exception {
        final var note = Note.builder().id(NOTES_ID).build();
        final var firstCategory = Category.builder().id(CAT_ID_1).build();
        final var secondCategory = Category.builder().id(CAT_ID_2).build();
        final var firstIngredient = Ingredient.builder().id(INGRED_ID_1).build();
        final var secondIngredient = Ingredient.builder().id(INGRED_ID_2).build();

        final var recipe = Recipe.builder()
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

        final var command = converter.convert(recipe);
        assertNotNull(command);

        assertEquals(URL, command.getUrl());
        assertEquals(RECIPE_ID, command.getId());
        assertEquals(SOURCE, command.getSource());
        assertEquals(SERVINGS, command.getServing());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(DIRECTION, command.getDirection());
        assertEquals(2, command.getCategories().size());
        assertEquals(2, command.getIngredients().size());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(NOTES_ID, command.getNote().getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }
}