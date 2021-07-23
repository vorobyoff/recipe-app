package ru.vorobyoff.recipeapp.services;

import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> findAllRecipes();

    Recipe findRecipeById(final Long id);

    RecipeCommand saveRecipeCommand(final RecipeCommand command);

    RecipeCommand findRecipeCommandById(final Long id);

    void deleteRecipeById(final Long id);
}