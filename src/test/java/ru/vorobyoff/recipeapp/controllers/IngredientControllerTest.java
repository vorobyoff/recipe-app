package ru.vorobyoff.recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.services.IngredientService;

import java.util.Set;

import static java.util.Collections.singleton;
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
    private IngredientService ingredientService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        openMocks(this);
        final var ingredientController = new IngredientController(ingredientService);
        mockMvc = standaloneSetup(ingredientController).build();
    }

    @Test
    void showIngredientsOfRecipeByRecipeIdMockMvcTest() throws Exception {
        final IngredientCommand ingredientCommand = IngredientCommand.builder().build();

        when(ingredientService.findIngredientCommandOfRecipeByItsId(anyLong())).thenReturn(singleton(ingredientCommand));

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(model().attribute("ingredients", Set.of(ingredientCommand)))
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(forwardedUrl("recipe/ingredient/list"))
                .andExpect(status().isOk());

        verify(ingredientService).findIngredientCommandOfRecipeByItsId(anyLong());
    }

    @Test
    void showIngredientOfRecipeByRecipeIdAndIngredientIdMockMvcTest() throws Exception {
        final var ingredient = Ingredient.builder().build();

        when(ingredientService.findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredient);

        mockMvc.perform(get("/recipe/1/ingredient/1"))
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(forwardedUrl("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(status().isOk());

        verify(ingredientService).findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong());
    }

    @Test
    void showIngredientsOfRecipeByRecipeIdNNotFoundCaseMockMvcTest() throws Exception {
        when(ingredientService.findIngredientCommandOfRecipeByItsId(anyLong())).thenThrow(new ResponseStatusException(NOT_FOUND));
        mockMvc.perform(get("/recipe/1/ingredients")).andExpect(status().isNotFound());
        verify(ingredientService).findIngredientCommandOfRecipeByItsId(anyLong());
    }
}