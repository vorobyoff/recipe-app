package ru.vorobyoff.recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.repositories.RecipeRepository;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
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
        assertEquals(recipes.size(), 1);

        verify(repository, times(1)).findAll();
    }
}