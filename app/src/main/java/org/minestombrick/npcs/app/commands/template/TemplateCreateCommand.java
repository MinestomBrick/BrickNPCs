package org.minestombrick.npcs.app.commands.template;

import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.entity.EntitySpawnType;
import net.minestom.server.entity.EntityType;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.npcs.api.NPCAPI;
import org.minestombrick.npcs.api.npc.NPCTemplate;

public class TemplateCreateCommand extends BrickCommand {

    public TemplateCreateCommand() {
        super("create");

        setCondition(b -> b.permission("bricknpcs.template.create"));

        ArgumentWord nameArg = new ArgumentWord("name");
        setInvalidUsageMessage("cmd.template.create.usage");

        ArgumentWord typeArg = new ArgumentWord("type").from(
                EntityType.values().stream()
                        .filter(type -> type.registry().spawnType() == EntitySpawnType.LIVING
                                || type == EntityType.PLAYER)
                        .map(type -> type.name().substring(10).toUpperCase())
                        .toArray(String[]::new));
        setInvalidArgumentMessage(typeArg, "cmd.error.args.type");

        addSyntax((sender, context) -> {
            String name = context.get(nameArg);
            if ( NPCAPI.get().template(name).isPresent() ) {
                I18nAPI.get(this).send(sender, "cmd.template.create.invalid", name);
                return;
            }

            NPCTemplate template = NPCAPI.get().newTemplate(name);
            template.setType(EntityType.fromNamespaceId(context.get(typeArg).toLowerCase()));

            NPCAPI.get().saveTemplate(template);

            I18nAPI.get(this).send(sender, "cmd.template.create", name);
        }, nameArg, typeArg);

    }

}
