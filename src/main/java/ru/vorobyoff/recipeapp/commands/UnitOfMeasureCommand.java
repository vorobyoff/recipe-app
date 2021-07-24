package ru.vorobyoff.recipeapp.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class UnitOfMeasureCommand extends BaseCommand {

    private String description;

    @Builder
    public UnitOfMeasureCommand(final Long id, final String description) {
        super(id);
        this.description = description;
    }
}