package ru.vorobyoff.recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.services.RecipeService;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

final class RecipeControllerTest {

    private static final String TEST_DESCRIPTION = "Description";
    private static final String TEST_DIRECTION = "Direction";
    private static final long TEST_ID = 1L;

    private RecipeController controller;
    @Mock
    private RecipeService service;
    private Recipe testRecipe;
    private MockMvc mockMvc;
    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        openMocks(this);
        controller = new RecipeController(service);
        mockMvc = standaloneSetup(controller).build();

        testRecipe = Recipe.builder()
                .description(TEST_DESCRIPTION)
                .direction(TEST_DIRECTION)
                .id(TEST_ID)
                .build();
    }

    @Test
    void showRecipeById() throws Exception {
        when(service.findRecipeById(anyLong())).thenReturn(testRecipe);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(model().attribute("recipe", testRecipe))
                .andExpect(view().name("recipe/show"))
                .andExpect(forwardedUrl("recipe/show"))
                .andExpect(status().isOk());

        verify(service).findRecipeById(anyLong());
    }

    @Test
    void showRecipeByIdNotFoundCase() {
        when(service.findRecipeById(anyLong())).thenThrow(new ResponseStatusException(NOT_FOUND));
        assertThrows(ResponseStatusException.class, () -> controller.showRecipeById(TEST_ID, model));
        verify(service).findRecipeById(anyLong());
    }

    @Test
    void createNewRecipe() throws Exception {

        mockMvc.perform(get("/recipe/new"))
                .andExpect(view().name("recipe/form"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(forwardedUrl("recipe/form"))
                .andExpect(status().isOk());

        verifyNoInteractions(service);
    }

    @Test
    void updateRecipeById() throws Exception {
        final var testRecipeCommand = RecipeCommand.builder().build();
        when(service.findRecipeCommandById(anyLong())).thenReturn(testRecipeCommand);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(model().attribute("recipe", testRecipeCommand))
                .andExpect(view().name("recipe/form"))
                .andExpect(forwardedUrl("recipe/form"))
                .andExpect(status().isOk());

        verify(service).findRecipeCommandById(anyLong());
    }

    @Test
    void deleteRecipeById() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(view().name("redirect:/index"))
                .andExpect(redirectedUrl("/index"))
                .andExpect(status().is3xxRedirection());

        verify(service).deleteRecipeById(anyLong());
    }

    @Test
    void createOrUpdateRecipe() throws Exception {
        final var testRecipeCommand = RecipeCommand.builder()
                .description(TEST_DESCRIPTION)
                .direction(TEST_DIRECTION)
                .id(TEST_ID)
                .build();

        when(service.saveRecipeCommand(any())).thenReturn(testRecipeCommand);

        mockMvc.perform(post("/recipe/")
                .contentType(APPLICATION_FORM_URLENCODED)
                .param("description", TEST_DESCRIPTION)
                .param("id", valueOf(TEST_ID)))
                .andExpect(view().name("redirect:1/show"))
                .andExpect(redirectedUrl("1/show"))
                .andExpect(status().is3xxRedirection());
    }
}