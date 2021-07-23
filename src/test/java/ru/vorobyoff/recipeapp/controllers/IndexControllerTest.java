package ru.vorobyoff.recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.services.RecipeService;

import static java.util.Collections.singleton;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class IndexControllerTest {

    @Mock
    private RecipeService recipeService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        openMocks(this);
        final var controller = new IndexController(recipeService);
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    void showIndexPage() throws Exception {
        final var testRecipe = Recipe.builder().build();
        when(recipeService.findAllRecipes()).thenReturn(singleton(testRecipe));

        mockMvc.perform(get("/"))
                .andExpect(model().attribute("recipes", singleton(testRecipe)))
                .andExpect(view().name("index"))
                .andExpect(forwardedUrl("index"))
                .andExpect(status().isOk());

        verify(recipeService).findAllRecipes();
    }
}