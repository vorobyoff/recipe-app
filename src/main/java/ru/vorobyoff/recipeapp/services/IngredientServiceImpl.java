package ru.vorobyoff.recipeapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.vorobyoff.recipeapp.commands.IngredientCommand;
import ru.vorobyoff.recipeapp.domain.Ingredient;
import ru.vorobyoff.recipeapp.domain.Recipe;
import ru.vorobyoff.recipeapp.repositories.IngredientRepository;
import ru.vorobyoff.recipeapp.repositories.RecipeRepository;
import ru.vorobyoff.recipeapp.repositories.UnitOfMeasureRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;
import static org.springframework.data.util.StreamUtils.createStreamFromIterator;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final Converter<Ingredient, IngredientCommand> toIngredientCommandConverter;
    private final Converter<IngredientCommand, Ingredient> toIngredientConverter;
    private final IngredientRepository ingredientRepository;
    private final UnitOfMeasureRepository uomRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public Set<Ingredient> findIngredientsOfRecipeByItsId(final Long recipeId) {
        final var ingredientIterator = ingredientRepository.findIngredientsByRecipe_Id(recipeId).iterator();
        return createStreamFromIterator(ingredientIterator).collect(toSet());
    }

    @Override
    @Transactional
    public Set<IngredientCommand> findIngredientCommandOfRecipeByItsId(final Long recipeId) {
        return findIngredientsOfRecipeByItsId(recipeId).stream()
                .map(toIngredientCommandConverter::convert)
                .filter(Objects::nonNull)
                .collect(toSet());
    }

    @Override
    public Ingredient findIngredientByRecipeIdAndIngredientId(final Long ingredientId, final Long recipeId) {
        return ingredientRepository.findIngredientsByIdAndRecipe_Id(ingredientId, recipeId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Ingredient with the given ids (" + ingredientId + ", " + recipeId + ") not found."));
    }

    @Override
    @Transactional
    public IngredientCommand findIngredientCommandByRecipeIdAndIngredientId(final Long ingredientId, final Long recipeId) {
        final var ingredient = findIngredientByRecipeIdAndIngredientId(ingredientId, recipeId);
        return toIngredientCommandConverter.convert(ingredient);
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(final IngredientCommand command) {
        final var recipe = recipeRepository.findById(command.getRecipeId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe with the given id does not exist."));

        findRecipeIngredientByItsId(command.getId(), recipe).ifPresentOrElse(
                ingredient -> updateExistingIngredientFromCommand(ingredient, command),
                () -> addNewIngredientCommandToRecipe(recipe, command)
        );

        return recipeRepository.save(recipe).getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst()
                .flatMap(ingredient -> ofNullable(toIngredientCommandConverter.convert(ingredient)))
                .orElseThrow();
    }

    @Override
    public void deleteIngredientById(final Long ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    private Optional<Ingredient> findRecipeIngredientByItsId(final Long ingredientId, final Recipe recipe) {
        if (isNull(ingredientId)) return empty();

        return recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();
    }

    private void updateExistingIngredientFromCommand(final Ingredient ingredient, final IngredientCommand command) {
        final var uom = uomRepository.findById(command.getUom().getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unit with the given id does not exist."));

        ingredient.setDescription(command.getDescription());
        ingredient.setAmount(command.getAmount());
        ingredient.setUom(uom);
    }

    private void addNewIngredientCommandToRecipe(final Recipe recipe, final IngredientCommand command) {
        final var saved = saveNewIngredientCommand(command);
        recipe.addIngredient(saved);
    }

    private Ingredient saveNewIngredientCommand(final IngredientCommand command) {
        final var convertedIngredient = toIngredientConverter.convert(command);
        final var saved = ingredientRepository.save(convertedIngredient);
        command.setId(saved.getId());

        return saved;
    }
}