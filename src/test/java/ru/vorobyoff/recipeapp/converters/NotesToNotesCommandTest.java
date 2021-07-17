package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.domain.Note;

import static org.junit.jupiter.api.Assertions.*;

public class NotesToNotesCommandTest {

    private static final String RECIPE_NOTES = "Note";
    private static final Long ID_VALUE = 1L;

    private NoteToNoteCommand converter;

    @BeforeEach
    public void setUp() {
        converter = new NoteToNoteCommand();
    }

    @Test
    public void convert() {
        final var notes = Note.builder()
                .recipeNote(RECIPE_NOTES)
                .id(ID_VALUE)
                .build();

        final var noteCommand = converter.convert(notes);
        assertNotNull(noteCommand);

        assertEquals(ID_VALUE, noteCommand.getId());
        assertEquals(RECIPE_NOTES, noteCommand.getRecipeNote());
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(Note.builder().build()));
    }
}