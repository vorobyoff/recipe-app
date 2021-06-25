package ru.vorobyoff.recipeapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
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
    private Set<Ingredient> ingredients;

    public Recipe(final Long id, final Integer prepTime, final Integer cookTime, final Integer serving,
                  final Integer source, final String url, final String description, final Note note,
                  final Byte[] image, final Set<Ingredient> ingredients) {
        this.id = id;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.serving = serving;
        this.source = source;
        this.url = url;
        this.description = description;
        this.note = note;
        this.image = image;
        this.ingredients = ingredients;
    }

    @Deprecated
    // Using only for JPA
    protected Recipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(final Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(final Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getServing() {
        return serving;
    }

    public void setServing(final Integer serving) {
        this.serving = serving;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(final Integer source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(final Note note) {
        this.note = note;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(final Byte[] image) {
        this.image = image;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(final Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}