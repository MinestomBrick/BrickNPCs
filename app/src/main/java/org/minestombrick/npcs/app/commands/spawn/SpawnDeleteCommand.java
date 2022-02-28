package org.minestombrick.npcs.app.commands.spawn;

import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.npcs.api.NPCAPI;
import org.minestombrick.npcs.api.npc.NPCSpawn;
import org.minestombrick.npcs.app.commands.arguments.ArgumentSpawn;

public class SpawnDeleteCommand extends BrickCommand {

    public SpawnDeleteCommand() {
        super("delete");

        setCondition(b -> b.permission("bricknpcs.spawn.delete"));

        ArgumentSpawn spawnArg = new ArgumentSpawn("spawn");
        setInvalidArgumentMessage(spawnArg, "cmd.error.args.spawn");

        setInvalidUsageMessage("cmd.spawn.delete.usage");

        addSyntax((sender, context) -> {
            NPCSpawn spawn = context.get(spawnArg);
            NPCAPI.get().deleteSpawn(spawn);
            I18nAPI.get(this).send(sender, "cmd.spawn.delete", spawn.name());
        }, spawnArg);
    }

}
