package ru.vorobyoff.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
}