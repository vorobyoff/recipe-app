package ru.vorobyoff.recipeapp.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.vorobyoff.recipeapp.domain.Difficulty;

import java.util.Set;

@Getter
@Setter
public final class RecipeCommand extends BaseCommand {

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

    @Builder
    public RecipeCommand(final Long id, final Set<IngredientCommand> ingredients, final Set<CategoryCommand> categories,
                         final Difficulty difficulty, final String description, final String direction, final Integer prepTime,
                         final Integer cookTime, final NoteCommand note, final Integer serving, final Integer source,
                         final Byte[] image, final String url) {
        super(id);
        this.ingredients = ingredients;
        this.categories = categories;
        this.difficulty = difficulty;
        this.description = description;
        this.direction = direction;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.note = note;
        this.serving = serving;
        this.source = source;
        this.image = image;
        this.url = url;
    }
}