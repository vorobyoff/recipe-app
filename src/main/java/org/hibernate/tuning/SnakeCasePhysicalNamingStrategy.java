package org.hibernate.tuning;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public final class SnakeCasePhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private static final String CAMEL_CASE_REGEX = "([a-z]+)([A-Z]+)";
    private static final String SNAKE_CASE_REPLACEMENT = "$1\\_$2";

    @Override
    public Identifier toPhysicalCatalogName(final Identifier name, final JdbcEnvironment context) {
        return super.toPhysicalCatalogName(toSnakeCase(name), context);
    }

    @Override
    public Identifier toPhysicalColumnName(final Identifier name, final JdbcEnvironment context) {
        return super.toPhysicalColumnName(toSnakeCase(name), context);
    }

    @Override
    public Identifier toPhysicalSchemaName(final Identifier name, final JdbcEnvironment context) {
        return super.toPhysicalSchemaName(toSnakeCase(name), context);
    }

    @Override
    public Identifier toPhysicalSequenceName(final Identifier name, final JdbcEnvironment context) {
        return super.toPhysicalSequenceName(toSnakeCase(name), context);
    }

    @Override
    public Identifier toPhysicalTableName(final Identifier name, final JdbcEnvironment context) {
        return super.toPhysicalTableName(toSnakeCase(name), context);
    }

    private Identifier toSnakeCase(final Identifier id) {
        if (id == null) return null;

        final var name = id.getText();
        final var snakeName = name.replaceAll(CAMEL_CASE_REGEX, SNAKE_CASE_REPLACEMENT).toLowerCase();
        return !snakeName.equals(name) ? new Identifier(snakeName, id.isQuoted()) : id;
    }
}