package com.gufli.bricknpcs.app.commands.template.edit;

import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.bricknpcs.app.commands.arguments.ArgumentTemplate;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minestom.server.command.builder.arguments.ArgumentString;

public class TemplateEditCustomNameCommand extends BrickCommand {

    public TemplateEditCustomNameCommand() {
        super("customname");

        setCondition(b -> b.permission("bricknpcs.template.edit.customname"));

        ArgumentTemplate templateArg = new ArgumentTemplate("template");
        setInvalidArgumentMessage(templateArg, "cmd.error.args.template");

        ArgumentString customNameArg = new ArgumentString("name");

        setInvalidUsageMessage("cmd.template.edit.customname.usage");

        addSyntax((sender, context) -> {
            NPCTemplate template = context.get(templateArg);
            Component customName = LegacyComponentSerializer.legacyAmpersand().deserialize(context.get(customNameArg));
            template.setCustomName(customName);

            NPCAPI.get().saveTemplate(template);
            TranslationAPI.get().send(sender, "cmd.template.edit.customname", template.name(), customName);
        }, templateArg, customNameArg);
    }
}
