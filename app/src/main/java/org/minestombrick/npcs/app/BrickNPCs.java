package org.minestombrick.npcs.app;

import com.google.gson.Gson;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.extensions.Extension;
import org.minestombrick.commandtools.api.command.CommandGroupBuilder;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.i18n.api.namespace.I18nNamespace;
import org.minestombrick.npcs.api.NPCAPI;
import org.minestombrick.npcs.api.NPCManager;
import org.minestombrick.npcs.app.commands.npc.NPCCreateCommand;
import org.minestombrick.npcs.app.commands.spawn.SpawnCreateCommand;
import org.minestombrick.npcs.app.commands.spawn.SpawnDeleteCommand;
import org.minestombrick.npcs.app.commands.spawn.SpawnListCommand;
import org.minestombrick.npcs.app.commands.spawn.edit.SpawnEditLookhereCommand;
import org.minestombrick.npcs.app.commands.spawn.edit.SpawnEditTeleporthereCommand;
import org.minestombrick.npcs.app.commands.template.TemplateCreateCommand;
import org.minestombrick.npcs.app.commands.template.TemplateDeleteCommand;
import org.minestombrick.npcs.app.commands.template.TemplateListCommand;
import org.minestombrick.npcs.app.commands.template.edit.TemplateEditCustomNameCommand;
import org.minestombrick.npcs.app.commands.template.edit.TemplateEditSkinCommand;
import org.minestombrick.npcs.app.commands.template.trait.TemplateTraitAddCommand;
import org.minestombrick.npcs.app.commands.template.trait.TemplateTraitListCommand;
import org.minestombrick.npcs.app.commands.template.trait.TemplateTraitRemoveCommand;
import org.minestombrick.npcs.app.config.BrickNPCsConfig;
import org.minestombrick.npcs.app.data.BrickNPCsDatabaseContext;

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
        I18nNamespace namespace = new I18nNamespace(this, Locale.ENGLISH);
        namespace.loadValues(this, "languages");
        I18nAPI.get().register(namespace);

        CommandManager gm = MinecraftServer.getCommandManager();
        gm.register(CommandGroupBuilder.of(new Command("bricknpcs", "bnpcs"))
                .group("npc", g -> g
                        .withCommand(new NPCCreateCommand())
                )
                .group("spawn", g -> g
                        .withCommand(new SpawnCreateCommand())
                        .withCommand(new SpawnDeleteCommand())
                        .withCommand(new SpawnListCommand())
                        .group("edit", g1 -> g1
                                .withCommand(new SpawnEditLookhereCommand())
                                .withCommand(new SpawnEditTeleporthereCommand())
                        )
                )
                .group("template", g -> g
                        .withCommand(new TemplateCreateCommand())
                        .withCommand(new TemplateDeleteCommand())
                        .withCommand(new TemplateListCommand())
                        .group("trait", g1 -> g1
                                .withCommand(new TemplateTraitAddCommand())
                                .withCommand(new TemplateTraitRemoveCommand())
                                .withCommand(new TemplateTraitListCommand())
                        )
                        .group("edit", g1 -> g1
                                .withCommand(new TemplateEditCustomNameCommand())
                                .withCommand(new TemplateEditSkinCommand())
                        )
                )
                .build()
        );

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
