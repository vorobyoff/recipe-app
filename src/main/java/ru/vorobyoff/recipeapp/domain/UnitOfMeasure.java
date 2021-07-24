package ru.vorobyoff.recipeapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(onConstructor_ = @Deprecated, access = PROTECTED)
public final class UnitOfMeasure extends BaseEntity {

    private String description;

    @Builder
    public UnitOfMeasure(final Long id, final String description) {
        super(id);
        this.description = description;
    }
}