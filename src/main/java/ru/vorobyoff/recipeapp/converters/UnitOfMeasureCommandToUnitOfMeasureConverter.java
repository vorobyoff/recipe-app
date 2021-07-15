package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

@Component
public final class UnitOfMeasureCommandToUnitOfMeasureConverter implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Override
    public UnitOfMeasure convert(final UnitOfMeasureCommand source) {
        return source == null ? null : UnitOfMeasure.builder().description(source.getDescription()).build();
    }
}