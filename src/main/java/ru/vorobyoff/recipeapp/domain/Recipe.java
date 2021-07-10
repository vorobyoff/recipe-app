package ru.vorobyoff.recipeapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(onConstructor_ = @Deprecated, access = PROTECTED)
public class Recipe {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NonNull
    private Integer prepTime;
    @NonNull
    private Integer cookTime;
    private Integer serving;
    private Integer source;
    private String url;
    @NonNull
    private String description;
    @OneToOne(cascade = ALL)
    private Note note;
    @Lob
    private Byte[] image;
    @OneToMany(cascade = ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();
    @NonNull
    @Enumerated(STRING)
    private Difficulty difficulty;
    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();
    @Lob
    @NonNull
    private String direction;

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