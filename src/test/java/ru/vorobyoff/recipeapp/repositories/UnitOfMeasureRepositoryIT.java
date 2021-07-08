package ru.vorobyoff.recipeapp.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataRetrievalFailureException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository repository;

    @Test
    void findByDescription() {
        final var unit = repository.findByDescription("Cup")
                .orElseThrow(() -> new DataRetrievalFailureException("No data available with this description."));

        assertEquals("Cup", unit.getDescription());
    }
}