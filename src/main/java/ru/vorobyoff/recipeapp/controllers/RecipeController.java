package ru.vorobyoff.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vorobyoff.recipeapp.services.RecipeService;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService service;

    public RecipeController(final RecipeService service) {
        this.service = service;
    }

    @GetMapping({"/show/{recipeId}"})
    public final String show(@PathVariable final Long recipeId, final Model model) {
        final var foundRecipe = service.getRecipeById(recipeId);
        model.addAttribute("recipe", foundRecipe);
        return "recipe/show";
    }
}