package ru.vorobyoff.recipeapp.services;

import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.domain.Ingredient;

public interface IngredientService {

    Iterable<Ingredient> findIngredientsOfRecipeByItsId(final Long recipeId);

    Iterable<IngredientCommand> findIngredientCommandOfRecipeByItsId(final Long recipeId);

    Ingredient findIngredientByRecipeIdAndIngredientId(final Long ingredientId, final Long recipeId);
}
