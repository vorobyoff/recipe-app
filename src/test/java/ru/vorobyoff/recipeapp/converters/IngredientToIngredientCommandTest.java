package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientToIngredientCommandTest {

    private static final Recipe RECIPE = Recipe.builder().build();
    private static final String DESCRIPTION = "Cheeseburger";
    private static final BigDecimal AMOUNT = ONE;
    private static final Long ID_VALUE = 1L;
    private static final Long UOM_ID = 2L;

    private IngredientToIngredientCommand converter;

    @BeforeEach
    public void setUp() {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testNullConvert() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(Ingredient.builder().build()));
    }

    @Test
    public void testConvertNullUOM() {
        final var ingredient = Ingredient.builder()
                .description(DESCRIPTION)
                .amount(AMOUNT)
                .recipe(RECIPE)
                .id(ID_VALUE)
                .uom(null)
                .build();

        final var ingredientCommand = converter.convert(ingredient);
        assertNotNull(ingredientCommand);

        assertNull(ingredientCommand.getUom());
        assertEquals(ID_VALUE, ingredientCommand.getId());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
    }

    @Test
    public void testConvertWithUom() {
        final var uom = UnitOfMeasure.builder().id(UOM_ID).build();

        final var ingredient = Ingredient.builder()
                .description(DESCRIPTION)
                .amount(AMOUNT)
                .recipe(RECIPE)
                .id(ID_VALUE)
                .uom(uom)
                .build();

        final var ingredientCommand = converter.convert(ingredient);
        assertNotNull(ingredientCommand);

        assertNotNull(ingredientCommand.getUom());
        assertEquals(UOM_ID, ingredientCommand.getUom().getId());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(ID_VALUE, ingredientCommand.getId());
    }
}