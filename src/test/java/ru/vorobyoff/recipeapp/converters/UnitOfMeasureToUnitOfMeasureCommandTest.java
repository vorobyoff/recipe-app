package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

final class UnitOfMeasureToUnitOfMeasureCommandTest {


    private static final String TEST_DESCRIPTION = "description";
    private static final Long TEST_ID = 1L;

    private UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void convert() throws Exception {
        final var command = UnitOfMeasure.builder()
                .description(TEST_DESCRIPTION)
                .id(TEST_ID)
                .build();

        final var uom = converter.convert(command);
        assertNotNull(uom);

        assertEquals(TEST_ID, uom.getId());
        assertEquals(TEST_DESCRIPTION, uom.getDescription());
    }

    @Test
    void convertNullPassedCase() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObjectPassedCase() {
        assertNotNull(converter.convert(UnitOfMeasure.builder().build()));
    }
}