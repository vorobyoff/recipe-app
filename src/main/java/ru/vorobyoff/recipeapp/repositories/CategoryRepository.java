package ru.vorobyoff.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vorobyoff.recipeapp.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}