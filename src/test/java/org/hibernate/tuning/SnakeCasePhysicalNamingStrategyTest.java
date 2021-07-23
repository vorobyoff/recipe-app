package org.hibernate.tuning;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.hibernate.boot.model.naming.Identifier.toIdentifier;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

final class SnakeCasePhysicalNamingStrategyTest {

    private static final String SNAKE_CASE_NAME = "camel_case_register_name";
    private static final String CAMEL_CASE_NAME = "camelCaseRegisterName";

    private PhysicalNamingStrategy namingStrategy;
    @Mock
    private JdbcEnvironment testEnv;
    private Identifier testId;

    @BeforeEach
    void setUp() {
        openMocks(this);
        namingStrategy = new SnakeCasePhysicalNamingStrategy();
        testId = toIdentifier(CAMEL_CASE_NAME);
    }

    @Test
    void toPhysicalCatalogName() {
        final var id = namingStrategy.toPhysicalCatalogName(testId, testEnv);
        assertNotNull(id);
        assertEquals(SNAKE_CASE_NAME, id.getText());
    }

    @Test
    void toPhysicalColumnName() {
        final var id = namingStrategy.toPhysicalColumnName(testId, testEnv);
        assertNotNull(id);
        assertEquals(SNAKE_CASE_NAME, id.getText());
    }

    @Test
    void toPhysicalSchemaName() {
        final var id = namingStrategy.toPhysicalSchemaName(testId, testEnv);
        assertNotNull(id);
        assertEquals(SNAKE_CASE_NAME, id.getText());
    }

    @Test
    void toPhysicalSequenceName() {
        final var id = namingStrategy.toPhysicalSequenceName(testId, testEnv);
        assertNotNull(id);
        assertEquals(SNAKE_CASE_NAME, id.getText());
    }

    @Test
    void toPhysicalTableName() {
        final var id = namingStrategy.toPhysicalTableName(testId, testEnv);
        assertNotNull(id);
        assertEquals(SNAKE_CASE_NAME, id.getText());
    }

    @Test
    void toPhysicalCatalogNameNullPassedCase() {
        final var id = namingStrategy.toPhysicalCatalogName(null, testEnv);
        assertNull(id);
    }

    @Test
    void toPhysicalColumnNameNullPassedCase() {
        final var id = namingStrategy.toPhysicalColumnName(null, testEnv);
        assertNull(id);
    }

    @Test
    void toPhysicalSchemaNameNullPassedCase() {
        final var id = namingStrategy.toPhysicalSchemaName(null, testEnv);
        assertNull(id);
    }

    @Test
    void toPhysicalSequenceNameNullPassedCase() {
        final var id = namingStrategy.toPhysicalSequenceName(null, testEnv);
        assertNull(id);
    }

    @Test
    void toPhysicalTableNameNullPassedCase() {
        final var id = namingStrategy.toPhysicalTableName(null, testEnv);
        assertNull(id);
    }
}