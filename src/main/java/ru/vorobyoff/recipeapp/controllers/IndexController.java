package ru.vorobyoff.recipeapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.vorobyoff.recipeapp.services.RecipeService;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final RecipeService recipeService;

    @GetMapping({"", "/", "/index"})
    public String showIndexPage(final Model model) {
        final var recipes = recipeService.getRecipes();
        model.addAttribute("recipes", recipes);
        return "index";
    }
}