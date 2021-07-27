package ru.vorobyoff.recipeapp.services;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletResponse;

public interface ImageService {

    void setUpRecipeImage(final Long recipeId, final MultipartFile image);

    void findImageByRecipeIdForServletResponse(final Long recipeId, final ServletResponse response);
}