package org.minestombrick.npcs.app.commands.npc;

import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.entity.EntitySpawnType;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.npcs.api.NPCAPI;
import org.minestombrick.npcs.api.npc.NPCSpawn;
import org.minestombrick.npcs.api.npc.NPCTemplate;

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
                I18nAPI.get(this).send(sender, "cmd.template.create.invalid", name);
                return;
            }

            if (NPCAPI.get().spawn(name).isPresent()) {
                I18nAPI.get(this).send(sender, "cmd.spawn.create.invalid", name);
                return;
            }

            NPCTemplate template = NPCAPI.get().newTemplate(name);
            template.setType(EntityType.fromNamespaceId(context.get(typeArg).toLowerCase()));

            Player player = (Player) sender;
            NPCSpawn spawn = NPCAPI.get().newSpawn(name, player.getPosition(), template);

            NPCAPI.get().saveTemplate(template).thenRun(() -> NPCAPI.get().saveSpawn(spawn));

            NPCAPI.get().spawn(player.getInstance(), spawn);
            I18nAPI.get(this).send(sender, "cmd.npc.create");
        }, nameArg, typeArg);
    }
}
