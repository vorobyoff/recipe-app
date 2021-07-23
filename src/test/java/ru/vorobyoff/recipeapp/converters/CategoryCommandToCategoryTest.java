package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.commands.CategoryCommand;

import static org.junit.jupiter.api.Assertions.*;

final class CategoryCommandToCategoryTest {

    private static final String TEST_DESCRIPTION = "Description";
    private static final Long TEST_ID = 1L;

    private CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void convert() {

        final var categoryCommand = CategoryCommand.builder()
                .description(TEST_DESCRIPTION)
                .id(TEST_ID)
                .build();

        final var category = converter.convert(categoryCommand);
        assertNotNull(category);

        assertEquals(TEST_DESCRIPTION, category.getDescription());
        assertEquals(TEST_ID, category.getId());
    }

    @Test
    void convertNullPassedCase() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObjectPassedCase() {
        assertNotNull(converter.convert(CategoryCommand.builder().build()));
    }
}