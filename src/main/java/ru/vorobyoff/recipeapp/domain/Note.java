package ru.vorobyoff.recipeapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(onConstructor_ = @Deprecated, access = PROTECTED)
public class Note extends BaseEntity {

    @OneToOne
    private Recipe recipe;
    @Lob
    private String recipeNote;

    @Builder
    public Note(final Long id, final Recipe recipe, final String recipeNote) {
        super(id);
        this.recipe = recipe;
        this.recipeNote = recipeNote;
    }
}