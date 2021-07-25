package ru.vorobyoff.recipeapp.services;

import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.domain.Ingredient;

import java.util.Set;

public interface IngredientService {

    IngredientCommand saveIngredientCommand(final IngredientCommand command);

    Set<Ingredient> findIngredientsOfRecipeByItsId(final Long recipeId);

    Set<IngredientCommand> findIngredientCommandOfRecipeByItsId(final Long recipeId);

    Ingredient findIngredientByRecipeIdAndIngredientId(final Long ingredientId, final Long recipeId);

    IngredientCommand findIngredientCommandByRecipeIdAndIngredientId(final Long ingredientId, final Long recipeId);

    void deleteIngredientById(final Long ingredientId);
}