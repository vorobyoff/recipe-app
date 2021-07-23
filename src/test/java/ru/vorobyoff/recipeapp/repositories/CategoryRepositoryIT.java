package ru.vorobyoff.recipeapp.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataRetrievalFailureException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryIT {

    @Autowired
    private CategoryRepository repository;

    @Test
    void findByDescription() {
        final var description = "American";
        final var category = repository.findByDescription(description)
                .orElseThrow(() -> new DataRetrievalFailureException("Not found."));

        assertEquals(description, category.getDescription());
    }

    @Test
    void findByDescriptionNotFoundCase() {
        final var categoryOpt = repository.findByDescription("Russian");
        assertNotNull(categoryOpt);
        assertTrue(categoryOpt.isEmpty());
    }
}