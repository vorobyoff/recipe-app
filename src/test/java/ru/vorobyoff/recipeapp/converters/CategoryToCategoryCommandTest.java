package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

final class CategoryToCategoryCommandTest {

    private static final String TEST_DESCRIPTION = "Description";
    private static final Long TEST_ID = 1L;

    private CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void convert() throws Exception {

        final var category = Category.builder()
                .description(TEST_DESCRIPTION)
                .id(TEST_ID)
                .build();

        final var categoryCommand = converter.convert(category);
        assertNotNull(categoryCommand);

        assertEquals(TEST_DESCRIPTION, categoryCommand.getDescription());
        assertEquals(TEST_ID, categoryCommand.getId());
    }

    @Test
    void convertNullPassedCase() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObjectPassedCase() {
        assertNotNull(converter.convert(Category.builder().build()));
    }
}