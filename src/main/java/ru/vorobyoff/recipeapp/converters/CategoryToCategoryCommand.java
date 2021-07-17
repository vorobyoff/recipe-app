package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.CategoryCommand;
import ru.vorobyoff.recipeapp.domain.Category;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) return null;

        return CategoryCommand.builder()
                .description(source.getDescription())
                .id(source.getId())
                .build();
    }
}
