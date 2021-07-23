package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.convert.converter.Converter;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

final class IngredientToIngredientCommandTest {

    private static final Recipe TEST_RECIPE = Recipe.builder().build();
    private static final String TEST_DESCRIPTION = "Cheeseburger";
    private static final BigDecimal TEST_AMOUNT = ONE;
    private static final Long TEST_ID = 1L;

    @Mock
    private Converter<UnitOfMeasure, UnitOfMeasureCommand> toUomCommandConverter;
    private IngredientToIngredientCommand converter;
    private Ingredient testIngredient;

    @BeforeEach
    void setUp() {
        openMocks(this);
        converter = new IngredientToIngredientCommand(toUomCommandConverter);

        final var testUom = UnitOfMeasure.builder().id(TEST_ID).build();
        testIngredient = Ingredient.builder()
                .description(TEST_DESCRIPTION)
                .amount(TEST_AMOUNT)
                .recipe(TEST_RECIPE)
                .uom(testUom)
                .id(TEST_ID)
                .build();
    }

    @Test
    void convert() {
        final var testUomCommand = UnitOfMeasureCommand.builder().id(TEST_ID).build();
        when(toUomCommandConverter.convert(any())).thenReturn(testUomCommand);

        final var ingredientCommand = converter.convert(testIngredient);
        assertNotNull(ingredientCommand);

        assertEquals(TEST_DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(TEST_AMOUNT, ingredientCommand.getAmount());
        assertEquals(TEST_ID, ingredientCommand.getId());

        final var uomCommand = ingredientCommand.getUom();
        assertNotNull(uomCommand);

        assertEquals(TEST_ID, uomCommand.getId());
    }

    @Test
    void convertNullPassedCase() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObjectPassedCase() {
        assertNotNull(converter.convert(Ingredient.builder().build()));
    }

    @Test
    void convertWithNullUom() {
        testIngredient.setUom(null);

        final var ingredientCommand = converter.convert(testIngredient);
        assertNotNull(ingredientCommand);

        assertEquals(TEST_ID, ingredientCommand.getId());
        assertEquals(TEST_AMOUNT, ingredientCommand.getAmount());
        assertEquals(TEST_DESCRIPTION, ingredientCommand.getDescription());

        assertNull(ingredientCommand.getUom());
    }
}