package ru.vorobyoff.recipeapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@MappedSuperclass
@Getter
@AllArgsConstructor
@NoArgsConstructor(onConstructor_ = @Deprecated, access = PROTECTED)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}