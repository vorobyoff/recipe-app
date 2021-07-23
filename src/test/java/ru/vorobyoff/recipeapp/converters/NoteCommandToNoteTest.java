package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.commands.NoteCommand;

import static org.junit.jupiter.api.Assertions.*;

final class NoteCommandToNoteTest {

    private static final String TEST_RECIPE_NOTE = "Note";
    private static final Long TEST_ID = 1L;

    private NoteCommandToNote converter;

    @BeforeEach
    void setUp() {
        converter = new NoteCommandToNote();
    }

    @Test
    void convert() {
        final var noteCommand = NoteCommand.builder()
                .recipeNote(TEST_RECIPE_NOTE)
                .id(TEST_ID)
                .build();

        final var note = converter.convert(noteCommand);
        assertNotNull(note);

        assertEquals(TEST_RECIPE_NOTE, note.getRecipeNote());
        assertEquals(TEST_ID, note.getId());
    }

    @Test
    void convertNullPassedCase() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObjectPassedCase() {
        assertNotNull(converter.convert(NoteCommand.builder().build()));
    }
}