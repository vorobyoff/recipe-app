package ru.vorobyoff.recipeapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    private Recipe recipe;
    @Lob
    private String recipeNote;

    public Note(final Long id, final Recipe recipe, final String recipeNote) {
        this.id = id;
        this.recipe = recipe;
        this.recipeNote = recipeNote;
    }

    public Note(final String recipeNote) {
        this.recipeNote = recipeNote;
    }

    @Deprecated
    // Using only for JPA
    public Note() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(final Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeNote() {
        return recipeNote;
    }

    public void setRecipeNote(final String recipeNote) {
        this.recipeNote = recipeNote;
    }
}