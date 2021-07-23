package ru.vorobyoff.recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.convert.converter.Converter;
import ru.vorobyoff.recipeapp.commands.UnitOfMeasureCommand;
import ru.vorobyoff.recipeapp.domain.UnitOfMeasure;
import ru.vorobyoff.recipeapp.repositories.UnitOfMeasureRepository;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UnitOfMeasureServiceImplTest {

    private static final String TEST_DESCRIPTION = "Description";
    private static final long TEST_ID = 1L;

    @Mock
    private Converter<UnitOfMeasure, UnitOfMeasureCommand> toCommandConverter;
    private UnitOfMeasureServiceImpl unitOfMeasureService;
    @Mock
    private UnitOfMeasureRepository repository;
    private UnitOfMeasureCommand testUomCommand;
    private UnitOfMeasure testUom;

    @BeforeEach
    void setUp() {
        openMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(toCommandConverter, repository);

        testUom = UnitOfMeasure.builder()
                .description(TEST_DESCRIPTION)
                .id(TEST_ID)
                .build();

        testUomCommand = UnitOfMeasureCommand.builder()
                .description(TEST_DESCRIPTION)
                .id(TEST_ID)
                .build();
    }

    @Test
    void findAllUnitCommands() {
        when(toCommandConverter.convert(any())).thenReturn(testUomCommand);
        when(repository.findAll()).thenReturn(singleton(testUom));

        final var foundCommands = unitOfMeasureService.findAllUnitCommands();
        assertNotNull(foundCommands);
        assertFalse(foundCommands.isEmpty());

        final var firstCommand = foundCommands.stream().findFirst().orElseThrow();
        assertEquals(TEST_DESCRIPTION, firstCommand.getDescription());
        assertEquals(TEST_ID, firstCommand.getId());

        verify(toCommandConverter, atLeastOnce()).convert(any());
        verify(repository).findAll();
    }

    @Test
    void findAllUnits() {
        when(repository.findAll()).thenReturn(singleton(testUom));

        final var foundUnits = unitOfMeasureService.findAllUnits();
        assertNotNull(foundUnits);
        assertFalse(foundUnits.isEmpty());

        final var firstUnit = foundUnits.stream().findFirst().orElseThrow();
        assertEquals(TEST_DESCRIPTION, firstUnit.getDescription());
        assertEquals(TEST_ID, firstUnit.getId());

        verify(toCommandConverter, never()).convert(any());
        verify(repository).findAll();
    }
}