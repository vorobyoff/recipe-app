package ru.vorobyoff.recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.repositories.RecipeRepository;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

class ImageServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;
    private ImageServiceImpl imgService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        imgService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    void setUpRecipeImage() throws IOException {
        final var recipeId = 1L;

        final var mockFile = new MockMultipartFile(
                "file",
                "some-file",
                TEXT_PLAIN_VALUE,
                new byte[]{2, 3, 9, 5, 2, 3});

        final var testRecipe = Recipe.builder().id(recipeId).build();
        final var captor = ArgumentCaptor.forClass(Recipe.class);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(testRecipe));

        imgService.setUpRecipeImage(recipeId, mockFile);

        verify(recipeRepository).findById(recipeId);
        verify(recipeRepository).save(captor.capture());

        final var savedRecipe = captor.getValue();
        assertEquals(mockFile.getBytes().length, savedRecipe.getImage().length);
    }
}