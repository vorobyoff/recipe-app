package ru.vorobyoff.recipeapp.services;

import ru.vorobyoff.recipeapp.domain.Ingredient;

public interface IngredientService {

    Iterable<Ingredient> findIngredientsOfRecipeByItsId(final Long recipeId);
}
