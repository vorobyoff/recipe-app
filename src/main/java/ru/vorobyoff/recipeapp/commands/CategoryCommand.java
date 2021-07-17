package ru.vorobyoff.recipeapp.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CategoryCommand extends BaseCommand {

    private Set<RecipeCommand> recipeCommands;
    private String description;

    @Builder
    public CategoryCommand(final Long id, final Set<RecipeCommand> recipeCommands, final String description) {
        super(id);
        this.recipeCommands = recipeCommands;
        this.description = description;
    }
}