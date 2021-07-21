package ru.vorobyoff.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.vorobyoff.recipeapp.services.RecipeService;

@Controller
public class IngredientController {

    private final RecipeService recipeService;

    public IngredientController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{recipeId}/ingredients")
    public final String showIngredientsOfRecipeByRecipeId(final @PathVariable Long recipeId, final Model model) {
        final var ingredientCommands = recipeService.getRecipeCommandById(recipeId).getIngredients();
        model.addAttribute("ingredients", ingredientCommands);
        return "recipe/ingredient/list";
    }
}