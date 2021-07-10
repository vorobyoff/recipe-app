package ru.vorobyoff.recipeapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(onConstructor_ = @Deprecated, access = PROTECTED)
public class Category extends BaseEntity {

    private String description;
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

    @Builder
    public Category(final Long id, final String description, final Set<Recipe> recipes) {
        super(id);
        this.description = description;
        this.recipes = isNull(recipes) ? new HashSet<>() : recipes;
    }
}