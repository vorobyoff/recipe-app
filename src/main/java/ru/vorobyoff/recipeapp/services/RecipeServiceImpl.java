package ru.vorobyoff.recipeapp.services;

import org.springframework.stereotype.Service;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository repository;

    public RecipeServiceImpl(final RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        final var recipes = new HashSet<Recipe>();
        repository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }
}
