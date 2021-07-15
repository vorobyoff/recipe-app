package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureConverterTest {

    private static final String DESCRIPTION = "Cup";

    private UnitOfMeasureCommandToUnitOfMeasureConverter converter;
    private UnitOfMeasureCommand command;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasureConverter();
        command = new UnitOfMeasureCommand(DESCRIPTION);
    }

    @Test
    void convert() {
        final var uom = converter.convert(command);
        assertNotNull(uom, "Converted value must not be null.");
        assertEquals(uom.getDescription(), command.getDescription());
    }

    @Test
    void convertNullPassedCase() {
        assertNull(converter.convert(null));
    }
}