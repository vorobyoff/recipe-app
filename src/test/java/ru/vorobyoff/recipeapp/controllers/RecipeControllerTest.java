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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class RecipeControllerTest {

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
    void showThroughMockMvc() throws Exception {
        when(service.getRecipeById(anyLong())).thenReturn(testRecipe);

        mockMvc.perform(get("/recipe/1/show"))
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

        final var viewName = controller.showRecipeById(TEST_ID, model);
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
        assertThrows(ResponseStatusException.class, () -> controller.showRecipeById(TEST_ID, model));
        verify(service).getRecipeById(anyLong());
    }

    @Test
    void createNewRecipe() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(view().name("recipe/form"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(forwardedUrl("recipe/form"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRecipeById() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(view().name("redirect:/index"))
                .andExpect(redirectedUrl("/index"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void postRecipeTest() throws Exception {
        when(service.saveRecipeCommand(any())).thenReturn(RecipeCommand.builder().id(TEST_ID).build());

        mockMvc.perform(post("/recipe/")
                .contentType(APPLICATION_FORM_URLENCODED)
                .param("description", "desc")
                .param("id", ""))
                .andExpect(view().name("redirect:1/show"))
                .andExpect(redirectedUrl("1/show"))
                .andExpect(status().is3xxRedirection());
    }
}