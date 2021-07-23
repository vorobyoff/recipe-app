package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.CategoryCommand;
import ru.vorobyoff.recipeapp.domain.Category;

import static java.util.Objects.isNull;

@Component
public final class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Nullable
    @Override
    public CategoryCommand convert(final Category category) {
        if (isNull(category)) return null;

        return CategoryCommand.builder()
                .description(category.getDescription())
                .id(category.getId())
                .build();
    }
}
