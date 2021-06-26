package ru.vorobyoff.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vorobyoff.recipeapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}