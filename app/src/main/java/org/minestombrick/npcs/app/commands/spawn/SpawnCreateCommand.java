package org.minestombrick.npcs.app.commands.spawn;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.entity.Player;
import org.apache.commons.lang3.RandomStringUtils;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.npcs.api.NPCAPI;
import org.minestombrick.npcs.api.npc.NPCSpawn;
import org.minestombrick.npcs.api.npc.NPCTemplate;
import org.minestombrick.npcs.app.commands.arguments.ArgumentTemplate;

public class SpawnCreateCommand extends BrickCommand {

    public SpawnCreateCommand() {
        super("create");

        setCondition(b -> b.permission("bricknpcs.spawn.create").playerOnly());

        ArgumentWord nameArg = new ArgumentWord("name");

        ArgumentTemplate templateArg = new ArgumentTemplate("template");
        setInvalidArgumentMessage(templateArg, "cmd.error.args.template");

        setInvalidUsageMessage("cmd.spawn.create.usage");

        addSyntax((sender, context) -> {
            String name = context.get(nameArg);
            if ( NPCAPI.get().spawn(name).isPresent() ) {
                I18nAPI.get(this).send(sender, "cmd.spawn.create.invalid", name);
                return;
            }

            NPCTemplate template = context.get(templateArg);
            create(sender, name, template);
        }, nameArg, templateArg);

        addSyntax((sender, context) -> {
            NPCTemplate template = context.get(templateArg);
            create(sender, RandomStringUtils.randomAlphanumeric(10), template);
        }, templateArg);
    }

    private void create(CommandSender sender, String name, NPCTemplate template) {
        Player player = (Player) sender;
        NPCSpawn spawn = NPCAPI.get().newSpawn(name, player.getPosition(), template);
        NPCAPI.get().saveSpawn(spawn);

        NPCAPI.get().spawn(player.getInstance(), spawn);
        I18nAPI.get(this).send(sender, "cmd.spawn.create", name);
    }
}
