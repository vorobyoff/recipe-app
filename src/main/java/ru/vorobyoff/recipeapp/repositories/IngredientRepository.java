package ru.vorobyoff.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vorobyoff.recipeapp.domain.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    Iterable<Ingredient> findIngredientsByRecipe_Id(final Long recipeId);

    Optional<Ingredient> findIngredientsByIdAndRecipe_Id(final Long ingredientId, final Long recipeId);
}