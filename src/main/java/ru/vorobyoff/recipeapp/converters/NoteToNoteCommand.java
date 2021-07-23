package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.NoteCommand;
import ru.vorobyoff.recipeapp.domain.Note;

import static java.util.Objects.isNull;

@Component
public final class NoteToNoteCommand implements Converter<Note, NoteCommand> {

    @Nullable
    @Override
    public NoteCommand convert(final Note note) {
        if (isNull(note)) return null;

        return NoteCommand.builder()
                .recipeNote(note.getRecipeNote())
                .id(note.getId())
                .build();
    }
}