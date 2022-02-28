package org.minestombrick.npcs.app.commands.spawn;

import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.npcs.api.NPCAPI;

import java.util.stream.Collectors;

public class SpawnListCommand extends BrickCommand {

    public SpawnListCommand() {
        super("list");

        setCondition(b -> b.permission("bricknpcs.spawn.list"));

        setDefaultExecutor((sender, context) -> {
            I18nAPI.get(this).send(sender, "cmd.spawn.list",
                    NPCAPI.get().spawns().stream()
                            .map(s -> s.name())
                            .collect(Collectors.joining(", "))
            );
        });
    }

}
