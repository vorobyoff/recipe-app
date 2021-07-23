package ru.vorobyoff.recipeapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.repositories.IngredientRepository;

import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.data.util.StreamUtils.createStreamFromIterator;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final Converter<Ingredient, IngredientCommand> ingredientConverter;
    private final IngredientRepository repository;

    @Override
    public Set<Ingredient> findIngredientsOfRecipeByItsId(final Long recipeId) {
        final var ingredientIterator = repository.findIngredientsByRecipe_Id(recipeId).iterator();
        return createStreamFromIterator(ingredientIterator).collect(toSet());
    }

    @Override
    @Transactional
    public Set<IngredientCommand> findIngredientCommandOfRecipeByItsId(final Long recipeId) {
        return findIngredientsOfRecipeByItsId(recipeId).stream()
                .map(ingredientConverter::convert)
                .filter(Objects::nonNull)
                .collect(toSet());
    }

    @Override
    public Ingredient findIngredientByRecipeIdAndIngredientId(final Long ingredientId, final Long recipeId) {
        return repository.findIngredientsByIdAndRecipe_Id(ingredientId, recipeId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Ingredient with the given ids (" + ingredientId + ", " + recipeId + ") not found."));
    }

    @Override
    @Transactional
    public IngredientCommand findIngredientCommandByRecipeIdAndIngredientId(final Long ingredientId, final Long recipeId) {
        final var ingredient = findIngredientByRecipeIdAndIngredientId(ingredientId, recipeId);
        return ingredientConverter.convert(ingredient);
    }
}