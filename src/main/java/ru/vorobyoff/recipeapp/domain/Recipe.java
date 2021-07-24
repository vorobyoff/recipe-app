package ru.vorobyoff.recipeapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(onConstructor_ = @Deprecated, access = PROTECTED)
public final class Recipe extends BaseEntity {

    private Integer prepTime;
    private Integer cookTime;
    private Integer serving;
    private Integer source;
    private String url;
    private String description;
    @OneToOne(cascade = ALL)
    private Note note;
    @Lob
    private Byte[] image;
    @OneToMany(cascade = ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();
    @Enumerated(STRING)
    private Difficulty difficulty;
    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();
    @Lob
    private String direction;

    @Builder
    public Recipe(final Long id, final Integer prepTime, final Integer cookTime, final Integer serving, final Integer source,
                  final String url, final String description, final Note note, final Byte[] image, final Set<Ingredient> ingredients,
                  final Difficulty difficulty, final Set<Category> categories, final String direction) {
        super(id);
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.serving = serving;
        this.source = source;
        this.url = url;
        this.description = description;
        this.note = note;
        this.image = image;
        this.ingredients = isNull(ingredients) ? new HashSet<>() : ingredients;
        this.difficulty = difficulty;
        this.categories = isNull(categories) ? new HashSet<>() : categories;
        this.direction = direction;
    }

    public void setNote(final Note note) {
        note.setRecipe(this);
        this.note = note;
    }

    public void addIngredient(final Ingredient ingredient) {
        ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }

    public void addIngredients(final Ingredient... ingredients) {
        for (final var ingredient : ingredients)
            addIngredient(ingredient);
    }

    public void addCategory(final Category category) {
        categories.add(category);
    }

    public void addCategories(final Category... categories) {
        for (final var category : categories)
            addCategory(category);
    }
}