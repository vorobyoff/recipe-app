package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.convert.converter.Converter;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

import static java.math.BigDecimal.ONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class IngredientCommandToIngredientConverterTest {

    private static final String TEST_DESCRIPTION = "Description";

    private IngredientCommandToIngredientConverter ingredientConverter;
    @Mock
    private Converter<UnitOfMeasureCommand, UnitOfMeasure> unitOfMeasureConverter;
    @Mock
    private Converter<RecipeCommand, Recipe> recipeConverter;

    private IngredientCommand ingredientTestCommand;
    private UnitOfMeasureCommand uomTestCommand;
    private RecipeCommand recipeTestCommand;

    @BeforeEach
    void setUp() {
        openMocks(this);
        ingredientConverter = new IngredientCommandToIngredientConverter(unitOfMeasureConverter, recipeConverter);
        initTestCommands();
    }

    @Test
    void convert() {
        when(unitOfMeasureConverter.convert(any())).thenReturn(UnitOfMeasure.builder().description(TEST_DESCRIPTION).build());
        when(recipeConverter.convert(any())).thenReturn(Recipe.builder().description(TEST_DESCRIPTION).build());

        final var ingredient = ingredientConverter.convert(ingredientTestCommand);
        assertNotNull(ingredient);

        assertNotNull(ingredient.getRecipe());
        assertEquals(ingredientTestCommand.getRecipeCommand().getDescription(), ingredient.getRecipe().getDescription());

        assertNotNull(ingredient.getUom());
        assertEquals(ingredientTestCommand.getUofCommand().getDescription(), ingredient.getUom().getDescription());

        assertEquals(ingredientTestCommand.getDescription(), ingredient.getDescription());
        assertEquals(ingredientTestCommand.getAmount(), ingredient.getAmount());

        verify(unitOfMeasureConverter).convert(any());
        verify(recipeConverter).convert(any());
    }

    @Test
    void convertNullPassedCase() {
        assertNull(ingredientConverter.convert(null));
        verify(unitOfMeasureConverter, never()).convert(any());
        verify(recipeConverter, never()).convert(any());
    }

    private void initTestCommands() {
        initRecipeCommand();
        initUomCommand();

        ingredientTestCommand = IngredientCommand.builder()
                .recipeCommand(recipeTestCommand)
                .description(TEST_DESCRIPTION)
                .uofCommand(uomTestCommand)
                .amount(ONE)
                .build();
    }

    private void initRecipeCommand() {
        recipeTestCommand = RecipeCommand.builder()
                .description(TEST_DESCRIPTION)
                .build();
    }

    private void initUomCommand() {
        uomTestCommand = new UnitOfMeasureCommand(TEST_DESCRIPTION);
    }
}