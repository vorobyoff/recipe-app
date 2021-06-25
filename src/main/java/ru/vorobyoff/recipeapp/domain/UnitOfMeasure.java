package ru.vorobyoff.recipeapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String description;

    public UnitOfMeasure(final Long id, final String description) {
        this.id = id;
        this.description = description;
    }

    @Deprecated
    // Using only for JPA
    public UnitOfMeasure() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String uom) {
        this.description = uom;
    }
}