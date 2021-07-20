package ru.vorobyoff.recipeapp.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;
import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.repositories.RecipeRepository;

@SpringBootTest
class RecipeServiceImplIT {

    private static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private Converter<RecipeCommand, Recipe> recipeCommandToRecipe;
    @Autowired
    private Converter<Recipe, RecipeCommand> recipeToRecipeCommand;

    @Test
    void getRecipes() {
    }

    @Test
    void getRecipeById() {
    }

    @Test
    void saveRecipeCommand() {
    }
}