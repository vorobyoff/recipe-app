package ru.vorobyoff.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vorobyoff.recipeapp.commands.NoteCommand;

import static org.junit.jupiter.api.Assertions.*;

public class NotesCommandToNotesTest {

    private static final String RECIPE_NOTES = "Note";
    private static final Long ID_VALUE = 1L;

    private NoteCommandToNote converter;

    @BeforeEach
    public void setUp() {
        converter = new NoteCommandToNote();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new NoteCommand()));
    }

    @Test
    public void convert() throws Exception {
        final var noteCommand = NoteCommand.builder().id(ID_VALUE).recipeNote(RECIPE_NOTES).build();

        final var note = converter.convert(noteCommand);
        assertNotNull(note);

        assertEquals(ID_VALUE, note.getId());
        assertEquals(RECIPE_NOTES, note.getRecipeNote());
    }
}