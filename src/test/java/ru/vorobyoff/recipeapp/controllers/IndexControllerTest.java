package ru.vorobyoff.recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.services.RecipeService;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static ru.vorobyoff.recipeapp.domain.Difficulty.EASY;
import static ru.vorobyoff.recipeapp.domain.Difficulty.MODERATE;

class IndexControllerTest {

    private IndexController controller;
    @Mock
    private RecipeService service;
    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        openMocks(this);
        controller = new IndexController(service);
    }

    @Test
    void testMockMvc() throws Exception {
        final var mock = MockMvcBuilders.standaloneSetup(controller).build();
        mock.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }

    @Test
    void getIndexPage() {
        final var recipes = Set.of(new Recipe(0, 0, "description", EASY, "direction"),
                new Recipe(1, 1, "description 1", MODERATE, "direction 1"));

        when(service.getRecipes()).thenReturn(recipes);

        final var captor = ArgumentCaptor.forClass(Set.class);

        final var indexPageHtmlTitle = controller.getIndexPAge(model);
        assertEquals("index", indexPageHtmlTitle);

        verify(service, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), captor.capture());

        final var controllerRecipes = captor.getValue();
        assertEquals(2, controllerRecipes.size());
    }
}