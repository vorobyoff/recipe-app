package ru.vorobyoff.recipeapp.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataRetrievalFailureException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository repository;

    @Test
    void findByDescription() {
        final var description = "Cup";
        final var unit = repository.findByDescription(description)
                .orElseThrow(() -> new DataRetrievalFailureException("No data available with this description."));

        assertEquals(description, unit.getDescription());
    }

    @Test
    void findByDescriptionNotfoundCase() {
        final var unit = repository.findByDescription("");
        assertNotNull(unit);
        assertTrue(unit.isEmpty());
    }
}