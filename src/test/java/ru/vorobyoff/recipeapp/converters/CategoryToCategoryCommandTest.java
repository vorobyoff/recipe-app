package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryToCategoryCommandTest {

    private static final String DESCRIPTION = "Description";
    private static final Long ID_VALUE = 1L;
    private CategoryToCategoryCommand converter;

    @BeforeEach
    public void setUp() throws Exception {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(Category.builder().build()));
    }

    @Test
    public void convert() throws Exception {
        final var category = Category.builder()
                .description(DESCRIPTION)
                .id(ID_VALUE)
                .build();

        final var categoryCommand = converter.convert(category);
        assertNotNull(categoryCommand);

        assertEquals(DESCRIPTION, categoryCommand.getDescription());
        assertEquals(ID_VALUE, categoryCommand.getId());
    }
}