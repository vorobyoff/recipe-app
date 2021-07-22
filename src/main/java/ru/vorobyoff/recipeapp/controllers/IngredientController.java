package ru.vorobyoff.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.vorobyoff.recipeapp.services.IngredientService;

@Controller
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(final IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("recipe/{recipeId}/ingredients")
    public final String showIngredientsOfRecipeByItsId(final @PathVariable Long recipeId, final Model model) {
        model.addAttribute("ingredients", ingredientService.findIngredientCommandOfRecipeByItsId(recipeId));
        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
    public final String showIngredientOfRecipeByRecipeIdAndIngredientId(final @PathVariable Long recipeId, final @PathVariable Long ingredientId, final Model model) {
        model.addAttribute("ingredient", ingredientService.findIngredientByRecipeIdAndIngredientId(ingredientId, recipeId));
        return "recipe/ingredient/show";
    }
}