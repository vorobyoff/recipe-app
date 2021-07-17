package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.CategoryCommand;
import ru.vorobyoff.recipeapp.domain.Category;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) return null;

        return Category.builder()
                .description(source.getDescription())
                .id(source.getId())
                .build();
    }
}
