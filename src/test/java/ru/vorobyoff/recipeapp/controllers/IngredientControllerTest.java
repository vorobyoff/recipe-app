package ru.vorobyoff.recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.services.IngredientService;
import ru.vorobyoff.recipeapp.services.UnitOfMeasureService;

import static java.util.Collections.singleton;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

final class IngredientControllerTest {

    @Mock
    private IngredientService ingredientService;
    @Mock
    private UnitOfMeasureService uomService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        openMocks(this);
        final var ingredientController = new IngredientController(ingredientService, uomService);
        mockMvc = standaloneSetup(ingredientController).build();
    }

    @Test
    void showIngredientsOfRecipeByRecipeIdMockMvcTest() throws Exception {
        final var ingredientCommands = singleton(IngredientCommand.builder().build());

        when(ingredientService.findIngredientCommandOfRecipeByItsId(anyLong())).thenReturn(ingredientCommands);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(model().attribute("ingredients", ingredientCommands))
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(forwardedUrl("recipe/ingredient/list"))
                .andExpect(status().isOk());

        verify(ingredientService).findIngredientCommandOfRecipeByItsId(anyLong());
    }

    @Test
    void showIngredientOfRecipeByRecipeIdAndIngredientIdMockMvcTest() throws Exception {
        final var testIngredient = Ingredient.builder().build();

        when(ingredientService.findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(testIngredient);

        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(model().attribute("ingredient", testIngredient))
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(forwardedUrl("recipe/ingredient/show"))
                .andExpect(status().isOk());

        verify(ingredientService).findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong());
    }

    @Test
    void showUpdateFormForIngredientByRecipeIdAndIngredientId() throws Exception {
        final var testUnitOfMeasureCommands = singleton(UnitOfMeasureCommand.builder().build());
        final var testIngredientCommand = IngredientCommand.builder().build();

        when(ingredientService.findIngredientCommandByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(testIngredientCommand);
        when(uomService.findAllUnitCommands()).thenReturn(testUnitOfMeasureCommands);

        mockMvc.perform(get("/recipe/{recipeId}/ingredient/{ingredientId}/update", 1L, 1L))
                .andExpect(model().attribute("ingredient", testIngredientCommand))
                .andExpect(model().attribute("units", testUnitOfMeasureCommands))
                .andExpect(view().name("recipe/ingredient/form"))
                .andExpect(forwardedUrl("recipe/ingredient/form"))
                .andExpect(status().isOk());

        verify(ingredientService).findIngredientCommandByRecipeIdAndIngredientId(anyLong(), anyLong());
        verify(uomService).findAllUnitCommands();
    }

    @Test
    void showIngredientsOfRecipeByRecipeIdNNotFoundCaseMockMvcTest() throws Exception {
        when(ingredientService.findIngredientCommandOfRecipeByItsId(anyLong())).thenThrow(new ResponseStatusException(NOT_FOUND));
        mockMvc.perform(get("/recipe/1/ingredients")).andExpect(status().isNotFound());
        verify(ingredientService).findIngredientCommandOfRecipeByItsId(anyLong());
    }
}