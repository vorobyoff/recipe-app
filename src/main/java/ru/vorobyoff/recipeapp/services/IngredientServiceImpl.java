package ru.vorobyoff.recipeapp.services;

import org.springframework.stereotype.Service;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.repositories.IngredientRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository repository;

    public IngredientServiceImpl(final IngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<Ingredient> findIngredientsOfRecipeByItsId(final Long recipeId) {
        final var ingredients = new HashSet<Ingredient>();
        repository.findIngredientsByRecipe_Id(recipeId).iterator().forEachRemaining(ingredients::add);
        return ingredients;
    }
}