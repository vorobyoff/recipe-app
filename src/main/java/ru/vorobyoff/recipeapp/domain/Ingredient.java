package ru.vorobyoff.recipeapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(onConstructor_ = @Deprecated, access = PROTECTED)
public class Ingredient extends BaseEntity {

    private String description;
    private BigDecimal amount;
    @ManyToOne
    private Recipe recipe;
    @OneToOne
    private UnitOfMeasure uom;

    @Builder
    public Ingredient(final Long id, final String description, final BigDecimal amount, final Recipe recipe, final UnitOfMeasure uom) {
        super(id);
        this.description = description;
        this.amount = amount;
        this.recipe = recipe;
        this.uom = uom;
    }
}