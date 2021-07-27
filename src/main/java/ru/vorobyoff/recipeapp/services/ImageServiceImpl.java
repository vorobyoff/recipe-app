package ru.vorobyoff.recipeapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.vorobyoff.recipeapp.repositories.RecipeRepository;

import javax.servlet.ServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.apache.tomcat.util.http.fileupload.IOUtils.copy;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    @Override
    @Transactional
    public void setUpRecipeImage(final Long recipeId, final MultipartFile image) {
        recipeRepository.findById(recipeId).ifPresentOrElse(recipe -> {
            final var boxedBytes = extractsContentFromMultipartFile(image);
            recipe.setImage(boxedBytes);
            recipeRepository.save(recipe);
        }, () -> {
            throw new ResponseStatusException(NOT_FOUND, "Recipe with the given id value does not exists.");
        });
    }

    @Override
    public void findImageByRecipeIdForServletResponse(final Long recipeId, final ServletResponse response) {
        recipeRepository.findById(recipeId).ifPresent(recipe -> {
            final var image = unboxBytes(recipe.getImage());
            final var stream = new ByteArrayInputStream(image);
            response.setContentType(IMAGE_JPEG_VALUE);

            try {
                copy(stream, response.getOutputStream());
            } catch (final IOException e) {
                throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
            }
        });
    }

    private Byte[] extractsContentFromMultipartFile(final MultipartFile image) {
        try {
            return boxBytes(image.getBytes());
        } catch (final IOException e) {
            throw new IllegalArgumentException("An error occurred while extracting the contents of a multipart file.", e);
        }
    }

    private byte[] unboxBytes(final Byte[] bytes) {
        final var unboxedBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; ++i)
            unboxedBytes[i] = bytes[i];
        return unboxedBytes;
    }

    private Byte[] boxBytes(final byte[] bytes) {
        final var unboxedBytes = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; ++i)
            unboxedBytes[i] = bytes[i];
        return unboxedBytes;
    }
}