package ru.vorobyoff.recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.services.RecipeService;

import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class IngredientControllerTest {

    @Mock
    private RecipeService recipeService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        openMocks(this);
        final IngredientController ingredientController = new IngredientController(recipeService);
        mockMvc = standaloneSetup(ingredientController).build();
    }

    @Test
    void showIngredientsOfRecipeByRecipeIdMockMvcTest() throws Exception {
        final IngredientCommand ingredientCommand = IngredientCommand.builder().build();
        final RecipeCommand recipeCommand = RecipeCommand.builder().ingredients(Set.of(ingredientCommand)).build();
        ingredientCommand.setRecipe(recipeCommand);

        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(model().attribute("ingredients", Set.of(ingredientCommand)))
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(forwardedUrl("recipe/ingredient/list"))
                .andExpect(status().isOk());

        verify(recipeService).getRecipeCommandById(anyLong());
    }

    @Test
    void showIngredientsOfRecipeByRecipeIdNNotFoundCaseMockMvcTest() throws Exception {
        when(recipeService.getRecipeCommandById(anyLong())).thenThrow(new ResponseStatusException(NOT_FOUND));
        mockMvc.perform(get("/recipe/2/ingredients")).andExpect(status().isNotFound());
        verify(recipeService).getRecipeCommandById(anyLong());
    }
}