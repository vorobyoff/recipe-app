package ru.vorobyoff.recipeapp.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class UnitOfMeasureCommand extends BaseCommand {

    private String description;

    @Builder
    public UnitOfMeasureCommand(final Long id, final String description) {
        super(id);
        this.description = description;
    }
}