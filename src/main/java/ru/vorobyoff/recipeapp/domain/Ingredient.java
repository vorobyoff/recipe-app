package ru.vorobyoff.recipeapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(onConstructor_ = @Deprecated, access = PROTECTED)
public class Ingredient {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NonNull
    private String description;
    @NonNull
    private BigDecimal amount;
    @NonNull
    @ManyToOne
    private Recipe recipe;
    @NonNull
    @OneToOne
    private UnitOfMeasure uom;
}