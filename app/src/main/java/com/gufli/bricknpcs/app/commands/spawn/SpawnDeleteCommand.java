package com.gufli.bricknpcs.app.commands.spawn;

import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.bricknpcs.api.npc.NPCSpawn;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.bricknpcs.app.commands.arguments.ArgumentSpawn;
import com.gufli.bricknpcs.app.commands.arguments.ArgumentTemplate;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationAPI;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.entity.Player;
import org.apache.commons.lang3.RandomStringUtils;

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
            TranslationAPI.get().send(sender, "cmd.spawn.delete", spawn.name());
        }, spawnArg);
    }

}
