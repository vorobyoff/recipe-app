package ru.vorobyoff.recipeapp.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public final class CategoryCommand extends BaseCommand {

    private Set<RecipeCommand> recipes;
    private String description;

    @Builder
    public CategoryCommand(final Long id, final Set<RecipeCommand> recipes, final String description) {
        super(id);
        this.recipes = recipes;
        this.description = description;
    }
}