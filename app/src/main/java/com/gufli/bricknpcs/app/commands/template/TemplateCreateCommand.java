package com.gufli.bricknpcs.app.commands.template;

import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationAPI;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.entity.EntitySpawnType;
import net.minestom.server.entity.EntityType;

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
                TranslationAPI.get().send(sender, "cmd.template.create.invalid", name);
                return;
            }

            NPCTemplate template = NPCAPI.get().newTemplate(name);
            template.setType(EntityType.fromNamespaceId(context.get(typeArg).toLowerCase()));

            NPCAPI.get().saveTemplate(template);

            TranslationAPI.get().send(sender, "cmd.template.create", name);
        }, nameArg, typeArg);

    }

}
