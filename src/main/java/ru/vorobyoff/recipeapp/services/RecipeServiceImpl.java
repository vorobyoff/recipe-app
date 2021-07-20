package ru.vorobyoff.recipeapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.vorobyoff.recipeapp.commands.RecipeCommand;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final Converter<Recipe, RecipeCommand> toRecipeCommandConverter;
    private final Converter<RecipeCommand, Recipe> toRecipeConverter;
    private final RecipeRepository repository;

    @Override
    public Set<Recipe> getRecipes() {
        final var recipes = new HashSet<Recipe>();
        repository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe getRecipeById(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe with the given id does not exist."));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(final RecipeCommand command) {
        if (command == null) throw new IllegalArgumentException("Parameter 'command' must not be null.");
        final var recipe = toRecipeConverter.convert(command);
        final var saved = repository.save(recipe);
        return toRecipeCommandConverter.convert(saved);
    }

    @Override
    @Transactional
    public RecipeCommand getRecipeCommandById(final Long id) {
        return toRecipeCommandConverter.convert(getRecipeById(id));
    }

    @Override
    public void deleteRecipeById(final Long id) {
        repository.deleteById(id);
    }
}