package ru.vorobyoff.recipeapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.services.RecipeService;

import static java.lang.String.format;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe/")
public final class RecipeController {

    private final RecipeService service;

    @GetMapping({"{recipeId}/show"})
    public final String showRecipeById(@PathVariable final Long recipeId, final Model model) {
        model.addAttribute("recipe", service.findRecipeById(recipeId));
        return "recipe/show";
    }

    @GetMapping("new")
    public final String createNewRecipe(final Model model) {
        model.addAttribute("recipe", RecipeCommand.builder().build());
        return "recipe/form";
    }

    @GetMapping({"{recipeId}/update"})
    public final String updateRecipeById(@PathVariable final Long recipeId, final Model model) {
        model.addAttribute("recipe", service.findRecipeCommandById(recipeId));
        return "recipe/form";
    }

    @GetMapping({"{recipeId}/delete"})
    public final String deleteRecipeById(@PathVariable final Long recipeId) {
        service.deleteRecipeById(recipeId);
        return "redirect:/index";
    }

    @PostMapping
    public final String createOrUpdateRecipe(@ModelAttribute final RecipeCommand command) {
        final var saved = service.saveRecipeCommand(command);
        return format("redirect:%1d/show", saved.getId());
    }
}