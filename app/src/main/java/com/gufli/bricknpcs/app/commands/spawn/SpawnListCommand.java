package com.gufli.bricknpcs.app.commands.spawn;

import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.bricknpcs.api.npc.NPCSpawn;
import com.gufli.bricknpcs.app.commands.arguments.ArgumentSpawn;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationAPI;
import com.gufli.brickutils.translation.TranslationManager;

import java.util.stream.Collectors;

public class SpawnListCommand extends BrickCommand {

    public SpawnListCommand() {
        super("list");

        setCondition(b -> b.permission("bricknpcs.spawn.list"));

        setDefaultExecutor((sender, context) -> {
            TranslationManager.get().send(sender, "cmd.spawn.list",
                    NPCAPI.get().spawns().stream()
                            .map(s -> s.name())
                            .collect(Collectors.joining(", "))
            );
        });
    }

}
