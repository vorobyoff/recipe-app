package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.NoteCommand;
import ru.vorobyoff.recipeapp.domain.Note;

@Component
public class NoteToNoteCommand implements Converter<Note, NoteCommand> {

    @Nullable
    @Override
    public NoteCommand convert(Note source) {
        if (source == null) return null;

        return NoteCommand.builder()
                .recipeNote(source.getRecipeNote())
                .id(source.getId())
                .build();
    }
}