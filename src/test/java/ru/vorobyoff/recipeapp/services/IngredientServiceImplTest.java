package ru.vorobyoff.recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.server.ResponseStatusException;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.repositories.IngredientRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static java.math.BigDecimal.ONE;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class IngredientServiceImplTest {

    private static final String TEST_DESCRIPTION = "Description";
    private static final BigDecimal TEST_AMOUNT = ONE;
    private static final Long TEST_ID = 1L;

    @Mock
    private Converter<Ingredient, IngredientCommand> ingredientConverter;
    @Mock
    private IngredientRepository ingredientRepository;
    private IngredientServiceImpl ingredientService;
    private Ingredient testIngredient;
    private IngredientCommand testCommand;

    @BeforeEach
    void setUp() {
        openMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientRepository, ingredientConverter);

        testIngredient = Ingredient.builder()
                .description(TEST_DESCRIPTION)
                .amount(TEST_AMOUNT)
                .id(TEST_ID)
                .build();

        testCommand = IngredientCommand.builder()
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
    void findIngredientCommandsOfRecipeByItsId() {
        when(ingredientRepository.findIngredientsByRecipe_Id(anyLong())).thenReturn(singleton(testIngredient));
        when(ingredientConverter.convert(any())).thenReturn(testCommand);

        final var foundCommands = ingredientService.findIngredientCommandOfRecipeByItsId(anyLong());
        assertNotNull(foundCommands);
        assertFalse(foundCommands.isEmpty());

        final var firstFound = foundCommands.stream().findFirst().orElseThrow();
        assertEquals(TEST_DESCRIPTION, firstFound.getDescription());
        assertEquals(TEST_AMOUNT, firstFound.getAmount());
        assertEquals(TEST_ID, firstFound.getId());

        verify(ingredientRepository).findIngredientsByRecipe_Id(anyLong());
        verify(ingredientConverter).convert(any());
    }

    @Test
    void findIngredientCommandsOfRecipeByItsIdNotFoundCase() {
        when(ingredientRepository.findIngredientsByRecipe_Id(anyLong())).thenReturn(emptySet());

        final var foundIngredients = ingredientService.findIngredientCommandOfRecipeByItsId(anyLong());
        assertNotNull(foundIngredients);
        assertTrue(foundIngredients.isEmpty());

        verify(ingredientRepository).findIngredientsByRecipe_Id(anyLong());
        verify(ingredientConverter, never()).convert(any());
    }

    @Test
    void findIngredientByRecipeIdAndIngredientId() {
        when(ingredientRepository.findIngredientsByIdAndRecipe_Id(anyLong(), anyLong())).thenReturn(Optional.of(testIngredient));

        final var foundIngredient = ingredientService.findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong());
        assertNotNull(foundIngredient);

        verify(ingredientRepository).findIngredientsByIdAndRecipe_Id(anyLong(), anyLong());
    }

    @Test
    void findIngredientByRecipeIdAndIngredientIdNotFoundCase() {
        when(ingredientRepository.findIngredientsByIdAndRecipe_Id(anyLong(), anyLong())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> ingredientService.findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong()));
        verify(ingredientRepository).findIngredientsByIdAndRecipe_Id(anyLong(), anyLong());
    }

    @Test
    void findIngredientsOfRecipeByItsIdNullPassedCase() {
        when(ingredientRepository.findIngredientsByRecipe_Id(anyLong())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> ingredientService.findIngredientsOfRecipeByItsId(anyLong()));
        verify(ingredientRepository).findIngredientsByRecipe_Id(anyLong());
    }
}