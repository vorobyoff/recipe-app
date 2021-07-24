package ru.vorobyoff.recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.server.ResponseStatusException;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;
import ru.vorobyoff.recipeapp.repositories.IngredientRepository;
import ru.vorobyoff.recipeapp.repositories.RecipeRepository;
import ru.vorobyoff.recipeapp.repositories.UnitOfMeasureRepository;

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
    private Converter<Ingredient, IngredientCommand> toIngredientCommandConverter;
    @Mock
    private Converter<IngredientCommand, Ingredient> toIngredientConverter;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private UnitOfMeasureRepository uomRepository;
    @Mock
    private RecipeRepository recipeRepository;
    private IngredientServiceImpl ingredientService;
    private IngredientCommand testIngredientCommand;
    private Ingredient testIngredient;

    @BeforeEach
    void setUp() {
        openMocks(this);
        ingredientService = new IngredientServiceImpl(
                toIngredientCommandConverter,
                toIngredientConverter,
                ingredientRepository,
                uomRepository,
                recipeRepository);

        testIngredient = Ingredient.builder()
                .description(TEST_DESCRIPTION)
                .amount(TEST_AMOUNT)
                .id(TEST_ID)
                .build();

        testIngredientCommand = IngredientCommand.builder()
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
        when(toIngredientCommandConverter.convert(any())).thenReturn(testIngredientCommand);

        final var foundCommands = ingredientService.findIngredientCommandOfRecipeByItsId(anyLong());
        assertNotNull(foundCommands);
        assertFalse(foundCommands.isEmpty());

        final var firstFound = foundCommands.stream().findFirst().orElseThrow();
        assertEquals(TEST_DESCRIPTION, firstFound.getDescription());
        assertEquals(TEST_AMOUNT, firstFound.getAmount());
        assertEquals(TEST_ID, firstFound.getId());

        verify(ingredientRepository).findIngredientsByRecipe_Id(anyLong());
        verify(toIngredientConverter).convert(any());
    }

    @Test
    void findIngredientCommandsOfRecipeByItsIdNotFoundCase() {
        when(ingredientRepository.findIngredientsByRecipe_Id(anyLong())).thenReturn(emptySet());

        final var foundIngredients = ingredientService.findIngredientCommandOfRecipeByItsId(anyLong());
        assertNotNull(foundIngredients);
        assertTrue(foundIngredients.isEmpty());

        verify(ingredientRepository).findIngredientsByRecipe_Id(anyLong());
        verify(toIngredientConverter, never()).convert(any());
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

    @Test
    void saveIngredientCommand() {

        final var testRecipe = Recipe.builder()
                .ingredients(singleton(testIngredient))
                .id(TEST_ID)
                .build();

        final var testUom = UnitOfMeasure.builder()
                .description(TEST_DESCRIPTION)
                .id(TEST_ID)
                .build();

        final var testUomCommand = UnitOfMeasureCommand.builder()
                .description(TEST_DESCRIPTION)
                .id(TEST_ID)
                .build();

        testIngredientCommand.setUom(testUomCommand);

        when(toIngredientCommandConverter.convert(any())).thenReturn(testIngredientCommand);
        when(recipeRepository.findById(any())).thenReturn(Optional.of(testRecipe));
        when(uomRepository.findById(anyLong())).thenReturn(Optional.of(testUom));
        when(recipeRepository.save(any())).thenReturn(testRecipe);

        final var savedCommand = ingredientService.saveIngredientCommand(testIngredientCommand);
        assertNotNull(savedCommand);

        assertEquals(TEST_DESCRIPTION, savedCommand.getDescription());
        assertEquals(TEST_AMOUNT, savedCommand.getAmount());
        assertEquals(TEST_ID, savedCommand.getId());

        final var uomCommand = savedCommand.getUom();
        assertNotNull(uomCommand);

        assertEquals(TEST_DESCRIPTION, uomCommand.getDescription());
        assertEquals(TEST_ID, uomCommand.getId());

        verify(toIngredientCommandConverter).convert(testIngredient);
        verify(recipeRepository).findById(testIngredientCommand.getRecipeId());
        verify(recipeRepository).save(testRecipe);
    }
}