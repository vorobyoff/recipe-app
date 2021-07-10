package ru.vorobyoff.recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.repositories.RecipeRepository;

import java.util.Set;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class RecipeServiceImplTest {

    private RecipeService service;
    @Mock
    private RecipeRepository repository;

    @BeforeEach
    void setUp() {
        openMocks(this);
        service = new RecipeServiceImpl(repository);
    }

    @Test
    void getRecipes() {
        final Set<Recipe> testRecipes = Set.of(Recipe.builder().build());

        when(repository.findAll()).thenReturn(testRecipes);

        final var recipes = service.getRecipes();
        assertEquals(testRecipes.size(), recipes.size());

        verify(repository).findAll();
    }

    @Test
    void getRecipesNotFoundCase() {
        when(repository.findAll()).thenReturn(emptySet());

        final var recipes = service.getRecipes();
        assertTrue(recipes.isEmpty());

        verify(repository).findAll();
    }
}