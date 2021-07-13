package ru.vorobyoff.recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.services.RecipeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    private static final long RECIPE_TEST_ID = 1L;
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
                .description("description")
                .direction("direction")
                .id(RECIPE_TEST_ID)
                .build();
    }

    @Test
    void showThroughMockMvc() throws Exception {
        when(service.getRecipeById(anyLong())).thenReturn(testRecipe);

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(forwardedUrl("recipe/show"))
                .andExpect(model().hasNoErrors())
                .andExpect(status().isOk());

        verify(service).getRecipeById(anyLong());
    }

    @Test
    void show() {
        when(service.getRecipeById(anyLong())).thenReturn(testRecipe);

        final var viewName = controller.show(RECIPE_TEST_ID, model);
        assertEquals("recipe/show", viewName);

        final var captor = forClass(Recipe.class);
        verify(model).addAttribute(anyString(), captor.capture());
        final var modelRecipe = captor.getValue();
        assertEquals(testRecipe.getId(), modelRecipe.getId());
        verify(service).getRecipeById(anyLong());
    }

    @Test
    void showNotFoundCase() {
        when(service.getRecipeById(anyLong())).thenThrow(new ResponseStatusException(NOT_FOUND, "Recipe with the given id does not exist."));
        assertThrows(ResponseStatusException.class, () -> controller.show(RECIPE_TEST_ID, model));
        verify(service).getRecipeById(anyLong());
    }
}