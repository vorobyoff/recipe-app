package ru.vorobyoff.recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vorobyoff.recipeapp.commands.CategoryCommand;
import ru.vorobyoff.recipeapp.domain.Category;

import static java.util.Objects.isNull;

@Component
public final class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Nullable
    @Override
    public Category convert(final CategoryCommand command) {
        if (isNull(command)) return null;

        return Category.builder()
                .description(command.getDescription())
                .id(command.getId())
                .build();
    }
}
