package ru.vorobyoff.recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.server.ResponseStatusException;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.repositories.RecipeRepository;

import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class RecipeServiceImplTest {

    private static final Long RECIPE_TEST_ID = 1L;

    private RecipeService service;
    @Mock
    private RecipeRepository repository;
    private Recipe testRecipe;

    @BeforeEach
    void setUp() {
        openMocks(this);
        service = new RecipeServiceImpl(repository);
        testRecipe = Recipe.builder().id(RECIPE_TEST_ID).build();
    }

    @Test
    void getRecipes() {
        when(repository.findAll()).thenReturn(Set.of(testRecipe));
        final var recipes = service.getRecipes();
        assertEquals(1, recipes.size());
        verify(repository).findAll();
    }

    @Test
    void getRecipesNotFoundCase() {
        when(repository.findAll()).thenReturn(emptySet());
        final var recipes = service.getRecipes();
        assertTrue(recipes.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void getRecipeById() {
        when(repository.findById(anyLong())).thenReturn(of(testRecipe));
        final var found = service.getRecipeById(anyLong());
        assertEquals(RECIPE_TEST_ID, found.getId());
        verify(repository, never()).findAll();
        verify(repository).findById(anyLong());
    }

    @Test
    void getRecipeByIdNotFoundCase() {
        when(repository.findById(anyLong())).thenReturn(empty());
        assertThrows(ResponseStatusException.class, () -> service.getRecipeById(anyLong()));
        verify(repository, never()).findAll();
        verify(repository).findById(anyLong());
    }
}