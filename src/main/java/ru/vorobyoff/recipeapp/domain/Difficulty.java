package ru.vorobyoff.recipeapp.domain;

public enum Difficulty {

    EASY("Easy"), MODERATE("Moderate"), KIND_OF_HARD("Kind of Hard"), HARD("Hard");

    private final String description;

    Difficulty(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}