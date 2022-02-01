package com.gufli.bricknpcs.app.commands.spawn;

import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.bricknpcs.api.npc.NPCSpawn;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.bricknpcs.app.commands.arguments.ArgumentTemplate;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationAPI;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentEntityType;
import net.minestom.server.entity.Player;

public class SpawnCreateCommand extends BrickCommand {

    public SpawnCreateCommand() {
        super("create");

        setCondition(b -> b.permission("bricknpcs.spawn.create"));

        ArgumentWord nameArg = new ArgumentWord("name");

        ArgumentTemplate templateArg = new ArgumentTemplate("template");
        setInvalidArgumentMessage(templateArg, "cmd.error.args.template");

        setInvalidUsageMessage("cmd.spawn.create.usage");

        addSyntax((sender, context) -> {
            String name = context.get(nameArg);
            if ( NPCAPI.get().spawn(name).isPresent() ) {
                TranslationAPI.get().send(sender, "cmd.spawn.create.invalid", name);
                return;
            }

            NPCTemplate template = context.get(templateArg);

            Player player = (Player) sender;
            NPCSpawn save = NPCAPI.get().newSpawn(name, player.getPosition(), template);
            NPCAPI.get().saveSpawn(save);

            NPCAPI.get().spawn(player.getInstance(), save);
            TranslationAPI.get().send(sender, "cmd.spawn.create");
        }, nameArg, templateArg);
    }
}
