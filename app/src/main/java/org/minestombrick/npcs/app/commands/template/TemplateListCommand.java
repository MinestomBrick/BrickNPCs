package org.minestombrick.npcs.app.commands.template;

import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.npcs.api.NPCAPI;
import org.minestombrick.npcs.api.npc.NPCSpawn;

import java.util.stream.Collectors;

public class TemplateListCommand extends BrickCommand {

    public TemplateListCommand() {
        super("list");

        setCondition(b -> b.permission("bricknpcs.spawn.list"));

        setDefaultExecutor((sender, context) -> {
            I18nAPI.get(this).send(sender, "cmd.template.list",
                    NPCAPI.get().spawns().stream()
                            .map(NPCSpawn::name)
                            .collect(Collectors.joining(", "))
            );
        });
    }

}
