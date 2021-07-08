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
import static ru.vorobyoff.recipeapp.domain.Difficulty.EASY;

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
        final var recipe = new Recipe(0, 1, "description", EASY, "direction");
        final Set<Recipe> testRecipes = Set.of(recipe);

        when(repository.findAll()).thenReturn(testRecipes);

        final var recipes = service.getRecipes();
        assertEquals(recipes.size(), 1);

        verify(repository, times(1)).findAll();
    }
}