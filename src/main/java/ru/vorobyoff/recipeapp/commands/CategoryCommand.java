package ru.vorobyoff.recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CategoryCommand {

    private Set<RecipeCommand> recipeCommands;
    private String description;
}