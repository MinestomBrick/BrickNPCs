package com.gufli.bricknpcs.app.commands.npc;

import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.bricknpcs.api.npc.NPCSpawn;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationAPI;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.entity.EntitySpawnType;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;

public class NPCCreateCommand extends BrickCommand {

    public NPCCreateCommand() {
        super("create");

        setCondition(b -> b.permission("bricknpcs.npc.create"));

        ArgumentWord nameArg = new ArgumentWord("name");

        ArgumentWord typeArg = new ArgumentWord("type").from(
                EntityType.values().stream()
                        .filter(type -> type.registry().spawnType() == EntitySpawnType.LIVING
                                || type == EntityType.PLAYER)
                        .map(type -> type.name().substring(10).toUpperCase())
                        .toArray(String[]::new));
        setInvalidArgumentMessage(typeArg, "cmd.error.args.type");

        setInvalidUsageMessage("cmd.npc.create.usage");

        addSyntax((sender, context) -> {
            String name = context.get(nameArg);
            if (NPCAPI.get().template(name).isPresent()) {
                TranslationAPI.get().send(sender, "cmd.template.create.invalid", name);
                return;
            }

            if (NPCAPI.get().spawn(name).isPresent()) {
                TranslationAPI.get().send(sender, "cmd.spawn.create.invalid", name);
                return;
            }


            NPCTemplate template = NPCAPI.get().newTemplate(name);
            template.setType(EntityType.fromNamespaceId(context.get(typeArg).toLowerCase()));

            Player player = (Player) sender;
            NPCSpawn save = NPCAPI.get().newSpawn(name, player.getPosition(), template);

            NPCAPI.get().saveTemplate(template).thenRun(() -> NPCAPI.get().saveSpawn(save));

            NPCAPI.get().spawn(player.getInstance(), save);
            TranslationAPI.get().send(sender, "cmd.npc.create");
        }, nameArg, typeArg);
    }
}
