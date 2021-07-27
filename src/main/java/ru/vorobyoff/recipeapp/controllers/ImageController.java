package ru.vorobyoff.recipeapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.vorobyoff.recipeapp.services.ImageService;
import ru.vorobyoff.recipeapp.services.RecipeService;

import javax.servlet.http.HttpServletResponse;

import static java.lang.String.format;

@Controller
@RequiredArgsConstructor
public final class ImageController {

    private final RecipeService recipeService;
    private final ImageService imgService;

    @GetMapping("recipe/{recipeId}/image")
    public String showUploadImageForm(@PathVariable final Long recipeId, final Model model) {
        model.addAttribute("recipe", recipeService.findRecipeCommandById(recipeId));
        return "recipe/image/form";
    }

    @PostMapping("recipe/{recipeId}/image")
    public String handleImagePosting(@PathVariable final Long recipeId, @RequestParam("image") final MultipartFile file) {
        imgService.setUpRecipeImage(recipeId, file);
        return format("redirect:/recipe/%d/show", recipeId);
    }

    @GetMapping("recipe/{recipeId}/img")
    public void provideRecipeImageByRecipeId(@PathVariable final Long recipeId, final HttpServletResponse response) {
        imgService.findImageByRecipeIdForServletResponse(recipeId, response);
    }
}