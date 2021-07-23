package ru.vorobyoff.recipeapp.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter
@AllArgsConstructor(access = PROTECTED)
public class BaseCommand {

    private Long id;
}