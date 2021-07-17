package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.NoteCommand;
import ru.vorobyoff.recipeapp.domain.Note;

@Component
public class NoteCommandToNote implements Converter<NoteCommand, Note> {

    @Nullable
    @Override
    public Note convert(NoteCommand source) {
        if (source == null) return null;

        return Note.builder()
                .recipeNote(source.getRecipeNote())
                .id(source.getId())
                .build();
    }
}