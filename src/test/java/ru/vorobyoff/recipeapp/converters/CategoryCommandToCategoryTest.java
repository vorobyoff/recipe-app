package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.commands.CategoryCommand;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryCommandToCategoryTest {

    private static final String DESCRIPTION = "Description";
    private static final Long ID_VALUE = 1L;
    private CategoryCommandToCategory converter;

    @BeforeEach
    public void setUp() throws Exception {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    public void convert() throws Exception {
        final var categoryCommand = CategoryCommand.builder()
                .description(DESCRIPTION)
                .id(ID_VALUE)
                .build();

        final var category = converter.convert(categoryCommand);
        assertNotNull(category);

        assertEquals(DESCRIPTION, category.getDescription());
        assertEquals(ID_VALUE, category.getId());
    }
}