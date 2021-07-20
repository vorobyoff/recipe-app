package ru.vorobyoff.recipeapp.services;

import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe getRecipeById(final Long id);

    RecipeCommand saveRecipeCommand(final RecipeCommand command);

    RecipeCommand getRecipeCommandById(final Long id);

    void deleteRecipeById(final Long id);
}
