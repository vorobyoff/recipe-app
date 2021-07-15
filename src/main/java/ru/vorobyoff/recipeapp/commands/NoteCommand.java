package ru.vorobyoff.recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoteCommand {

    private RecipeCommand recipeCommand;
    private String recipeNote;
}