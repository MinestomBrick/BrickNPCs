package org.minestombrick.npcs.app.commands.template.edit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minestom.server.command.builder.arguments.ArgumentString;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.npcs.api.NPCAPI;
import org.minestombrick.npcs.api.npc.NPCTemplate;
import org.minestombrick.npcs.app.commands.arguments.ArgumentTemplate;

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
            I18nAPI.get(this).send(sender, "cmd.template.edit.customname", template.name(), customName);
        }, templateArg, customNameArg);
    }
}
