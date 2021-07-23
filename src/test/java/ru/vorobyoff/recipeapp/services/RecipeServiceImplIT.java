package ru.vorobyoff.recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.domain.Difficulty;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.repositories.RecipeRepository;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.vorobyoff.recipeapp.domain.Difficulty.EASY;

@SpringBootTest
class RecipeServiceImplIT {

    private static final String TEST_DESCRIPTION = "Description";
    private static final String TEST_DIRECTION = "Direction";
    private static final Difficulty TEST_DIFFICULTY = EASY;
    private static final Byte[] TEST_IMG = {1, 0, 1};
    private static final int TEST_PREP_TIME = 0;
    private static final int TEST_COOK_TIME = 1;
    private static final int TEST_SERVING = 2;
    private static final int TEST_SOURCE = 3;
    private static final Long TEST_ID = 4L;

    @Autowired
    private RecipeService recipeService;
    @MockBean
    private RecipeRepository repository;
    private Recipe testRecipe;

    @BeforeEach
    void setUp() {
        testRecipe = Recipe.builder()
                .description(TEST_DESCRIPTION)
                .difficulty(TEST_DIFFICULTY)
                .direction(TEST_DIRECTION)
                .prepTime(TEST_PREP_TIME)
                .cookTime(TEST_COOK_TIME)
                .serving(TEST_SERVING)
                .source(TEST_SOURCE)
                .image(TEST_IMG)
                .id(TEST_ID)
                .build();
    }

    @Test
    void getRecipes() {
        when(repository.findAll()).thenReturn(Set.of(testRecipe));

        final var foundRecipes = recipeService.findAllRecipes();
        assertNotNull(foundRecipes);
        assertFalse(foundRecipes.isEmpty());

        final var firstFoundRecipe = foundRecipes.stream().findFirst().orElseThrow(IllegalStateException::new);
        validateRecipe(firstFoundRecipe);

        verify(repository).findAll();
    }

    @Test
    void getRecipeById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(testRecipe));

        final var foundRecipe = recipeService.findRecipeById(anyLong());
        assertNotNull(foundRecipe);
        validateRecipe(foundRecipe);

        verify(repository).findById(anyLong());
    }

    @Test
    void saveRecipeCommand() {
        when(repository.save(any())).thenReturn(testRecipe);

        final var savedCommand = recipeService.saveRecipeCommand(RecipeCommand.builder().id(TEST_ID).build());
        assertNotNull(savedCommand);
        assertEquals(TEST_ID, savedCommand.getId());

        verify(repository).save(any());
    }

    @Test
    void getRecipeCommandById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(testRecipe));

        final var foundCommand = recipeService.findRecipeCommandById(anyLong());
        assertNotNull(foundCommand);
        assertEquals(TEST_ID, foundCommand.getId());

        verify(repository).findById(anyLong());
    }

    @Test
    void deleteRecipeById() {
        recipeService.deleteRecipeById(anyLong());
        verify(repository).deleteById(anyLong());
    }

    private void validateRecipe(final Recipe recipe) {
        assertEquals(TEST_DESCRIPTION, recipe.getDescription());
        assertEquals(TEST_IMG.length, recipe.getImage().length);
        assertEquals(TEST_DIFFICULTY, recipe.getDifficulty());
        assertEquals(TEST_DIRECTION, recipe.getDirection());
        assertEquals(TEST_COOK_TIME, recipe.getCookTime());
        assertEquals(TEST_PREP_TIME, recipe.getPrepTime());
        assertEquals(TEST_SERVING, recipe.getServing());
        assertEquals(TEST_SOURCE, recipe.getSource());
        assertEquals(TEST_ID, recipe.getId());
    }
}