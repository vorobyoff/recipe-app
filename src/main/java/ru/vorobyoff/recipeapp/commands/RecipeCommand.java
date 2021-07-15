package ru.vorobyoff.recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vorobyoff.recipeapp.domain.Difficulty;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private Set<IngredientCommand> ingredients;
    private Set<CategoryCommand> categories;
    private Difficulty difficulty;
    private String description;
    private String direction;
    private Integer prepTime;
    private Integer cookTime;
    private NoteCommand note;
    private Integer serving;
    private Integer source;
    private Byte[] image;
    private String url;

}
