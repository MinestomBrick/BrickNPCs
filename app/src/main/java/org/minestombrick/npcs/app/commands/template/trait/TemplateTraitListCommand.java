package org.minestombrick.npcs.app.commands.template.trait;

import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.npcs.api.npc.NPCTemplate;
import org.minestombrick.npcs.app.commands.arguments.ArgumentTemplate;

public class TemplateTraitListCommand extends BrickCommand {

    public TemplateTraitListCommand() {
        super("list");

        setCondition(b -> b.permission("bricknpcs.template.trait.list"));

        ArgumentTemplate templateArg = new ArgumentTemplate("template");
        setInvalidArgumentMessage(templateArg, "cmd.error.args.template");

        setInvalidUsageMessage("cmd.template.trait.list.usage");

        addSyntax((sender, context) -> {
            NPCTemplate template = context.get(templateArg);
            I18nAPI.get(this).send(sender, "cmd.template.trait.list",
                    template.name(),
                    String.join(", ", template.traits())
            );
        }, templateArg);
    }

}
