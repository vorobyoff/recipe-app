package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.convert.converter.Converter;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

final class IngredientCommandToIngredientTest {

    private static final String TEST_DESCRIPTION = "Cheeseburger";
    private static final BigDecimal TEST_AMOUNT = ONE;
    private static final Long TEST_ID = 1L;

    @Mock
    private Converter<UnitOfMeasureCommand, UnitOfMeasure> toUnitOfMeasureConverter;
    private IngredientCommandToIngredient converter;
    private IngredientCommand testIngredientCommand;

    @BeforeEach
    void setUp() {
        openMocks(this);
        converter = new IngredientCommandToIngredient(toUnitOfMeasureConverter);

        final var testUomCommand = UnitOfMeasureCommand.builder().id(TEST_ID).build();
        testIngredientCommand = IngredientCommand.builder()
                .description(TEST_DESCRIPTION)
                .amount(TEST_AMOUNT)
                .uom(testUomCommand)
                .id(TEST_ID)
                .build();
    }

    @Test
    void convert() {
        final var testUom = UnitOfMeasure.builder().id(TEST_ID).build();

        when(toUnitOfMeasureConverter.convert(any())).thenReturn(testUom);

        final var ingredient = converter.convert(testIngredientCommand);
        assertNotNull(ingredient);

        assertEquals(TEST_ID, ingredient.getId());
        assertEquals(TEST_AMOUNT, ingredient.getAmount());
        assertEquals(TEST_DESCRIPTION, ingredient.getDescription());

        final var uom = ingredient.getUom();
        assertNotNull(uom);

        assertEquals(TEST_ID, uom.getId());
    }

    @Test
    void convertNullPassedCase() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObjectPassedCase() {
        assertNotNull(converter.convert(IngredientCommand.builder().build()));
    }

    @Test
    void convertWithNullUOM() {
        testIngredientCommand.setUom(null);

        final var ingredient = converter.convert(testIngredientCommand);
        assertNotNull(ingredient);

        assertEquals(TEST_ID, ingredient.getId());
        assertEquals(TEST_AMOUNT, ingredient.getAmount());
        assertEquals(TEST_DESCRIPTION, ingredient.getDescription());

        assertNull(ingredient.getUom());
    }
}