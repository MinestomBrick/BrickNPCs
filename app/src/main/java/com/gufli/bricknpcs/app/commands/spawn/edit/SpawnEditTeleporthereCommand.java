package com.gufli.bricknpcs.app.commands.spawn.edit;

import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.bricknpcs.api.npc.NPCSpawn;
import com.gufli.bricknpcs.app.commands.arguments.ArgumentSpawn;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationAPI;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.position.PositionUtils;

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

            TranslationAPI.get().send(sender, "cmd.spawn.edit.lookhere", spawn.name());
        }, spawnArg);
    }
}
