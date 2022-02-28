package com.gufli.bricknpcs;

import org.minestombrick.ebean.migration.MigrationGenerator;
import org.minestombrick.npcs.app.data.BrickNPCsDatabaseContext;
import io.ebean.annotation.Platform;

import java.io.IOException;
import java.nio.file.Path;

public class GenerateDbMigration {

    /**
     * Generate the next "DB schema DIFF" migration.
     */
    public static void main(String[] args) throws IOException {
        MigrationGenerator generator = new MigrationGenerator(
                BrickNPCsDatabaseContext.DATASOURCE_NAME,
                Path.of("BrickNPCs/app/src/main/resources"),
                Platform.H2, Platform.MYSQL
        );
        BrickNPCsDatabaseContext.classes().forEach(generator::addClass);
        generator.generate();
    }
}