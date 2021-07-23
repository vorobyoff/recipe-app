package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;

import static org.junit.jupiter.api.Assertions.*;

final class UnitOfMeasureCommandToUnitOfMeasureTest {

    private static final String TEST_DESCRIPTION = "Description";
    private static final Long TEST_ID = 1L;

    private UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void convert() throws Exception {
        final var command = UnitOfMeasureCommand.builder()
                .description(TEST_DESCRIPTION)
                .id(TEST_ID)
                .build();

        final var uom = converter.convert(command);
        assertNotNull(uom);

        assertEquals(TEST_DESCRIPTION, uom.getDescription());
        assertEquals(TEST_ID, uom.getId());
    }

    @Test
    void convertNullPassedCase() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObjectPassedCase() {
        assertNotNull(converter.convert(UnitOfMeasureCommand.builder().build()));
    }
}