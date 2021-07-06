package ru.vorobyoff.recipeapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(onConstructor_ = @Deprecated, access = PROTECTED)
public class Note {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    private Recipe recipe;
    @NonNull
    @Lob
    private String recipeNote;
}