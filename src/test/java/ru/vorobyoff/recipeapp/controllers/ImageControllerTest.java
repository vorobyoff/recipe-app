package ru.vorobyoff.recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.services.ImageService;
import ru.vorobyoff.recipeapp.services.RecipeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class ImageControllerTest {

    private static final String TEST_ORIG_FILE_NAME = "original.txt";
    private static final byte[] TEST_CONTENT = {8, 6, 3, 8, 5};
    private static final String TEST_FILE_NAME = "image";

    @Mock
    private RecipeService recipeService;
    @Mock
    private ImageService imgService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        openMocks(this);
        final var controller = new ImageController(recipeService, imgService);
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    void showUploadImageForm() throws Exception {
        final var recipeId = 1L;
        final var testRecipeCommand = RecipeCommand.builder()
                .id(recipeId)
                .build();

        when(recipeService.findRecipeCommandById(anyLong())).thenReturn(testRecipeCommand);

        mockMvc.perform(get("/recipe/{recipeId}/image", recipeId))
                .andExpect(model().attribute("recipe", testRecipeCommand))
                .andExpect(view().name("recipe/image/form"))
                .andExpect(status().isOk());

        verify(imgService, never()).setUpRecipeImage(anyLong(), any());
        verify(recipeService).findRecipeCommandById(anyLong());
    }

    @Test
    void handleImagePosting() throws Exception {

        final var mockFile = new MockMultipartFile(
                TEST_FILE_NAME,
                TEST_ORIG_FILE_NAME,
                TEXT_PLAIN_VALUE,
                TEST_CONTENT);

        mockMvc.perform(multipart("/recipe/{recipeId}/image", 1L).file(mockFile))
                .andExpect(redirectedUrl("/recipe/1/show"))
                .andExpect(status().is3xxRedirection());

        verify(recipeService, never()).findRecipeCommandById(anyLong());
        verify(imgService).setUpRecipeImage(anyLong(), any());
    }
}