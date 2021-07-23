package ru.vorobyoff.recipeapp.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class NoteCommand extends BaseCommand {

    private RecipeCommand recipe;
    private String recipeNote;

    @Builder
    public NoteCommand(final Long id, final RecipeCommand recipe, final String recipeNote) {
        super(id);
        this.recipe = recipe;
        this.recipeNote = recipeNote;
    }
}