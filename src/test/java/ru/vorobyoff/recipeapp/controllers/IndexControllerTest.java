package ru.vorobyoff.recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.ui.Model;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.services.RecipeService;

import java.util.Set;

import static java.util.Set.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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
        final var mock = standaloneSetup(controller).build();
        mock.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }

    @Test
    void getIndexPage() {
        final var recipes = of(Recipe.builder().build());
        when(service.getRecipes()).thenReturn(recipes);
        final var captor = forClass(Set.class);
        assertEquals("index", controller.getIndexPage(model));
        verify(service).getRecipes();
        verify(model).addAttribute(eq("recipes"), captor.capture());
        final var controllerRecipes = captor.getValue();
        assertEquals(1, controllerRecipes.size());
    }
}