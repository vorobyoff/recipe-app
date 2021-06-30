package ru.vorobyoff.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByDescription(final String description);
}