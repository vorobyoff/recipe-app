package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.Recipe;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientCommandToIngredientTest {

    private static final Recipe RECIPE = Recipe.builder().build();
    private static final String DESCRIPTION = "Cheeseburger";
    private static final BigDecimal AMOUNT = ONE;
    private static final Long ID_VALUE = 1L;
    private static final Long UOM_ID = 2L;

    private IngredientCommandToIngredient converter;

    @BeforeEach
    public void setUp() throws Exception {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    public void convert() throws Exception {
        final var unitOfMeasureCommand = UnitOfMeasureCommand.builder().id(UOM_ID).build();

        final var command = IngredientCommand.builder()
                .uofCommand(unitOfMeasureCommand)
                .description(DESCRIPTION)
                .amount(AMOUNT)
                .id(ID_VALUE)
                .build();

        final var ingredient = converter.convert(command);

        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(UOM_ID, ingredient.getUom().getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }

    @Test
    public void convertWithNullUOM() {
        final var command = IngredientCommand.builder()
                .description(DESCRIPTION)
                .amount(AMOUNT)
                .id(ID_VALUE)
                .build();

        final var ingredient = converter.convert(command);

        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }
}