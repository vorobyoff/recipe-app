package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

import static java.util.Objects.isNull;

@Component
public final class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Nullable
    @Override
    public UnitOfMeasureCommand convert(final UnitOfMeasure unitOfMeasure) {
        if (isNull(unitOfMeasure)) return null;

        return UnitOfMeasureCommand.builder()
                .description(unitOfMeasure.getDescription())
                .id(unitOfMeasure.getId())
                .build();

    }
}