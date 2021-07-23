package ru.vorobyoff.recipeapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;
import ru.vorobyoff.recipeapp.repositories.UnitOfMeasureRepository;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.data.util.StreamUtils.createStreamFromIterator;

@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final Converter<UnitOfMeasure, UnitOfMeasureCommand> toCommandConverter;
    private final UnitOfMeasureRepository repository;

    @Override
    public Set<UnitOfMeasureCommand> findAllUnitCommands() {
        return createStreamFromIterator(repository.findAll().iterator())
                .map(toCommandConverter::convert)
                .collect(toSet());
    }

    @Override
    public Set<UnitOfMeasure> findAllUnits() {
        return createStreamFromIterator(repository.findAll().iterator())
                .collect(toSet());
    }
}