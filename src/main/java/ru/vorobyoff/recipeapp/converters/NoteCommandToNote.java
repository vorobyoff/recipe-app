package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.NoteCommand;
import ru.vorobyoff.recipeapp.domain.Note;

import static java.util.Objects.isNull;

@Component
public final class NoteCommandToNote implements Converter<NoteCommand, Note> {

    @Nullable
    @Override
    public Note convert(final NoteCommand command) {
        if (isNull(command)) return null;

        return Note.builder()
                .recipeNote(command.getRecipeNote())
                .id(command.getId())
                .build();
    }
}