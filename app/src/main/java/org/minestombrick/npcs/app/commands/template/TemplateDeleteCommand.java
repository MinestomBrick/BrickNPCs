package org.minestombrick.npcs.app.commands.template;

import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.npcs.api.NPCAPI;
import org.minestombrick.npcs.api.npc.NPCTemplate;
import org.minestombrick.npcs.app.commands.arguments.ArgumentTemplate;

public class TemplateDeleteCommand extends BrickCommand {

    public TemplateDeleteCommand() {
        super("delete");

        setCondition(b -> b.permission("bricknpcs.template.delete"));

        ArgumentTemplate templateArg = new ArgumentTemplate("template");
        setInvalidArgumentMessage(templateArg, "cmd.error.args.template");

        setInvalidUsageMessage("cmd.template.delete.usage");

        addSyntax((sender, context) -> {
            NPCTemplate template = context.get(templateArg);
            NPCAPI.get().deleteTemplate(template);
            I18nAPI.get(this).send(sender, "cmd.template.delete", template.name());
        }, templateArg);
    }

}
