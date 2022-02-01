package com.gufli.bricknpcs.app;

import com.google.gson.Gson;
import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.bricknpcs.api.NPCManager;
import com.gufli.bricknpcs.app.commands.BrickNPCCommand;
import com.gufli.bricknpcs.app.config.BrickNPCsConfig;
import com.gufli.bricknpcs.app.data.BrickNPCsDatabaseContext;
import com.gufli.brickutils.translation.SimpleTranslationManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class BrickNPCs extends Extension {

    private static final Gson gson = new Gson();

    private BrickNPCsDatabaseContext databaseContext;

    @Override
    public void initialize() {
        getLogger().info("Enabling " + nameAndVersion() + ".");

        // load config
        BrickNPCsConfig config;
        try (
                InputStream is = getResource("config.json");
                InputStreamReader isr = new InputStreamReader(is)
        ) {
            config = gson.fromJson(isr, BrickNPCsConfig.class);
        } catch (IOException e) {
            getLogger().error("Cannot load configuration.", e);
            return;
        }

        // DATABASE
        databaseContext = new BrickNPCsDatabaseContext();
        try {
            databaseContext.withContextClassLoader(() -> {
                databaseContext.init(
                        config.database.dsn,
                        config.database.username,
                        config.database.password
                );

                NPCManager npcManager = new BrickNPCManager(databaseContext);
                NPCAPI.setNpcManager(npcManager);
                return null;
            });
        } catch (Exception e) {
            getLogger().error("Cannot initialize database context.", e);
            return;
        }

        // TRANSLATIONS
        SimpleTranslationManager tm = new SimpleTranslationManager(this, Locale.ENGLISH);
        tm.loadTranslations(this, "languages");

        MinecraftServer.getCommandManager().register(new BrickNPCCommand());

        getLogger().info("Enabled " + nameAndVersion() + ".");
    }

    @Override
    public void terminate() {

        getLogger().info("Disabled " + nameAndVersion() + ".");
    }

    private String nameAndVersion() {
        return getOrigin().getName() + " v" + getOrigin().getVersion();
    }

}
