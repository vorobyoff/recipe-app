package ru.vorobyoff.recipeapp.services;

import ru.vorobyoff.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe getRecipeById(final Long id);
}
