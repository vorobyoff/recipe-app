package ru.vorobyoff.recipeapp.services;

import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> findAllUnitCommands();

    Set<UnitOfMeasure> findAllUnits();
}