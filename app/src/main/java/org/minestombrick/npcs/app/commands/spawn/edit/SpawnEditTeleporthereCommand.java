package org.minestombrick.npcs.app.commands.spawn.edit;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.npcs.api.NPCAPI;
import org.minestombrick.npcs.api.npc.NPCSpawn;
import org.minestombrick.npcs.app.commands.arguments.ArgumentSpawn;

public class SpawnEditTeleporthereCommand extends BrickCommand {

    public SpawnEditTeleporthereCommand() {
        super("teleporthere");

        setCondition(b -> b.permission("bricknpcs.spawn.edit.teleporthere").playerOnly());

        ArgumentSpawn spawnArg = new ArgumentSpawn("spawn");
        setInvalidArgumentMessage(spawnArg, "cmd.error.args.spawn");

        setInvalidUsageMessage("cmd.spawn.edit.teleporthere.usage");

        addSyntax((sender, context) -> {
            NPCSpawn spawn = context.get(spawnArg);
            Pos pos = ((Player) sender).getPosition();
            spawn.setPosition(pos);
            NPCAPI.get().saveSpawn(spawn);

            I18nAPI.get(this).send(sender, "cmd.spawn.edit.lookhere", spawn.name());
        }, spawnArg);
    }
}
