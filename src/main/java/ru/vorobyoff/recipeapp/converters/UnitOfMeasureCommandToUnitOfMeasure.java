package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

import static java.util.Objects.isNull;

@Component
public final class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Nullable
    @Override
    public UnitOfMeasure convert(final UnitOfMeasureCommand command) {
        if (isNull(command)) return null;

        return UnitOfMeasure.builder()
                .description(command.getDescription())
                .id(command.getId())
                .build();
    }
}