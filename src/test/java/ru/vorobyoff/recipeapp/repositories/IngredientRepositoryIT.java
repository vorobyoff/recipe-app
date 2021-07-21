package ru.vorobyoff.recipeapp.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.Recipe;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IngredientRepositoryIT {

    private static final String TEST_DESCRIPTION = "Description";
    private static final BigDecimal TEST_AMOUNT = ONE;

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    private Ingredient testIngredient;
    private Recipe testRecipe;

    @BeforeEach
    void setUp() {
        final var tempRecipe = Recipe.builder()
                .description(TEST_DESCRIPTION)
                .build();

        testRecipe = recipeRepository.save(tempRecipe);

        final var tempIngredient = Ingredient.builder()
                .description(TEST_DESCRIPTION)
                .amount(TEST_AMOUNT)
                .recipe(testRecipe)
                .build();

        testIngredient = ingredientRepository.save(tempIngredient);
    }

    @Test
    void findIngredientsByRecipe_Id() {
        final var foundIngredients = ingredientRepository.findIngredientsByRecipe_Id(testRecipe.getId());
        assertNotNull(foundIngredients);
        assertTrue(foundIngredients.iterator().hasNext());

        final var firstFound = foundIngredients.iterator().next();
        assertNotNull(firstFound);

        assertEquals(TEST_DESCRIPTION, firstFound.getDescription());
        assertEquals(testIngredient.getId(), firstFound.getId());
        assertEquals(TEST_AMOUNT, firstFound.getAmount());
    }

    @AfterEach
    void tearDown() {
        ingredientRepository.delete(testIngredient);
        recipeRepository.delete(testRecipe);
    }
}