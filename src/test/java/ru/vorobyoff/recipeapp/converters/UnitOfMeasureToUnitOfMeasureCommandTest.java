package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {


    private static final String DESCRIPTION = "description";
    private static final Long LONG_VALUE = 1L;

    private UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    public void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(UnitOfMeasure.builder().build()));
    }

    @Test
    public void convert() throws Exception {
        final var command = UnitOfMeasure.builder()
                .description(DESCRIPTION)
                .id(LONG_VALUE)
                .build();

        final var uom = converter.convert(command);

        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}