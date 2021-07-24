package ru.vorobyoff.recipeapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.services.IngredientService;
import ru.vorobyoff.recipeapp.services.UnitOfMeasureService;

import static java.lang.String.format;

@Controller
@RequiredArgsConstructor
public final class IngredientController {

    private final IngredientService ingredientService;
    private final UnitOfMeasureService uomService;

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

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public final String showUpdateFormForIngredientByRecipeIdAndIngredientId(final @PathVariable Long recipeId, final @PathVariable Long ingredientId, final Model model) {
        model.addAttribute("ingredient", ingredientService.findIngredientCommandByRecipeIdAndIngredientId(ingredientId, recipeId));
        model.addAttribute("units", uomService.findAllUnitCommands());

        return "recipe/ingredient/form";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public final String saveOrUpdateIngredient(final @ModelAttribute IngredientCommand command, @PathVariable final Long recipeId) {
        if (!command.getRecipeId().equals(recipeId))
            throw new IllegalArgumentException(format("Parameter 'recipeId' (%1d) and 'command.getRecipeId()' (%2d) are not equal.", recipeId, command.getRecipeId()));

        final var savedCommand = ingredientService.saveIngredientCommand(command);
        return format("redirect:/recipe/%1d/ingredient/%2d/show", savedCommand.getRecipeId(), savedCommand.getId());
    }
}