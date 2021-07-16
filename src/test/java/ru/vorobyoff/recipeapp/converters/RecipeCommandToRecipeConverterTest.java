package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.convert.converter.Converter;
import ru.vorobyoff.recipeapp.commands.CategoryCommand;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.commands.NoteCommand;
import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.domain.Category;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.Note;

import java.util.Set;

import static java.math.BigDecimal.ONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static ru.vorobyoff.recipeapp.domain.Difficulty.EASY;

class RecipeCommandToRecipeConverterTest {

    private static final String TEST_DESCRIPTION = "Description";

    @Mock
    private Converter<IngredientCommand, Ingredient> ingredientConverter;
    @Mock
    private Converter<CategoryCommand, Category> categoryConverter;
    @Mock
    private Converter<NoteCommand, Note> noteConverter;
    private RecipeCommandToRecipeConverter recipeConverter;

    private IngredientCommand ingredientTestCommand;
    private CategoryCommand categoryTestCommand;
    private RecipeCommand recipeTestCommand;
    private NoteCommand noteTestCommand;

    @BeforeEach
    void setUp() {
        openMocks(this);
        recipeConverter = new RecipeCommandToRecipeConverter(ingredientConverter, categoryConverter, noteConverter);
        initTestCommands();
    }

    @Test
    void convert() {
        when(ingredientConverter.convert(any())).thenReturn(Ingredient.builder()
                .description(TEST_DESCRIPTION)
                .amount(ONE)
                .build());

        when(categoryConverter.convert(any())).thenReturn(Category.builder()
                .description(TEST_DESCRIPTION)
                .build());

        when(noteConverter.convert(any())).thenReturn(Note.builder()
                .recipeNote("Note's content.")
                .build());

        final var recipe = recipeConverter.convert(recipeTestCommand);
        assertNotNull(recipe);

        assertNotNull(recipeTestCommand.getCategories());
        assertEquals(recipeTestCommand.getCategories().size(), recipe.getCategories().size());

        assertNotNull(recipeTestCommand.getIngredients());
        assertEquals(recipeTestCommand.getIngredients().size(), recipe.getIngredients().size());

        assertNotNull(recipeTestCommand.getNote());
        assertEquals(recipeTestCommand.getNote().getRecipeNote(), recipe.getNote().getRecipeNote());

        assertEquals(recipeTestCommand.getDescription(), recipe.getDescription());

        verify(ingredientConverter).convert(any());
        verify(categoryConverter).convert(any());
        verify(noteConverter).convert(any());
    }

    @Test
    void convertNullPassedCase() {
        assertNull(recipeConverter.convert(null));
        verify(ingredientConverter, never()).convert(any());
        verify(categoryConverter, never()).convert(any());
        verify(noteConverter, never()).convert(any());
    }

    private void initTestCommands() {
        initIngredientTestCommand();
        initCategoryTestCommand();
        initNoteTestCommand();
        initRecipeTestCommand();

        categoryTestCommand.setRecipeCommands(Set.of(recipeTestCommand));
        ingredientTestCommand.setRecipeCommand(recipeTestCommand);
        noteTestCommand.setRecipeCommand(recipeTestCommand);
    }

    private void initIngredientTestCommand() {
        ingredientTestCommand = IngredientCommand.builder()
                .description(TEST_DESCRIPTION)
                .amount(ONE)
                .build();
    }

    private void initCategoryTestCommand() {
        categoryTestCommand = CategoryCommand.builder()
                .description(TEST_DESCRIPTION)
                .build();
    }

    private void initNoteTestCommand() {
        noteTestCommand = NoteCommand.builder()
                .recipeNote("Note's content.")
                .build();
    }

    private void initRecipeTestCommand() {
        recipeTestCommand = RecipeCommand.builder()
                .ingredients(Set.of(ingredientTestCommand))
                .categories(Set.of(categoryTestCommand))
                .description(TEST_DESCRIPTION)
                .cookTime(ONE.intValue())
                .prepTime(ONE.intValue())
                .direction("Direction")
                .url("http://www.test.com")
                .serving(ONE.intValue())
                .image(new Byte[]{0, 1})
                .note(noteTestCommand)
                .difficulty(EASY)
                .build();
    }
}