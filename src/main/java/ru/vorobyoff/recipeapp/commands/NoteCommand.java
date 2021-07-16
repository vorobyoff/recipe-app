package ru.vorobyoff.recipeapp.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteCommand {

    private RecipeCommand recipeCommand;
    private String recipeNote;
}