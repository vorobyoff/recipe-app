package ru.vorobyoff.recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.repositories.IngredientRepository;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class IngredientServiceImplTest {

    private static final String TEST_DESCRIPTION = "Description";
    private static final BigDecimal TEST_AMOUNT = ONE;
    private static final Long TEST_ID = 1L;

    @Mock
    private IngredientRepository ingredientRepository;
    private IngredientServiceImpl ingredientService;
    private Ingredient testIngredient;

    @BeforeEach
    void setUp() {
        openMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientRepository);

        testIngredient = Ingredient.builder()
                .description(TEST_DESCRIPTION)
                .amount(TEST_AMOUNT)
                .id(TEST_ID)
                .build();
    }

    @Test
    void findIngredientsOfRecipeByItsId() {
        when(ingredientRepository.findIngredientsByRecipe_Id(anyLong())).thenReturn(singleton(testIngredient));

        final var foundIngredients = ingredientService.findIngredientsOfRecipeByItsId(anyLong());
        assertNotNull(foundIngredients);
        assertFalse(foundIngredients.isEmpty());

        final var firstFound = foundIngredients.stream().findFirst().orElseThrow();
        assertEquals(TEST_DESCRIPTION, firstFound.getDescription());
        assertEquals(TEST_AMOUNT, firstFound.getAmount());
        assertEquals(TEST_ID, firstFound.getId());

        verify(ingredientRepository).findIngredientsByRecipe_Id(anyLong());
    }

    @Test
    void findIngredientsOfRecipeByItsIdNotFoundCase() {
        when(ingredientRepository.findIngredientsByRecipe_Id(anyLong())).thenReturn(emptySet());

        final var foundIngredients = ingredientService.findIngredientsOfRecipeByItsId(anyLong());
        assertNotNull(foundIngredients);
        assertTrue(foundIngredients.isEmpty());

        verify(ingredientRepository).findIngredientsByRecipe_Id(anyLong());
    }

    @Test
    void findIngredientsOfRecipeByItsIdNullPassedCase() {
        when(ingredientRepository.findIngredientsByRecipe_Id(anyLong())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> ingredientService.findIngredientsOfRecipeByItsId(anyLong()));
        verify(ingredientRepository).findIngredientsByRecipe_Id(anyLong());
    }
}