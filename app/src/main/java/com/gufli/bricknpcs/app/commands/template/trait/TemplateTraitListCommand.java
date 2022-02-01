package com.gufli.bricknpcs.app.commands.template.trait;

import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.bricknpcs.app.commands.arguments.ArgumentTemplate;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationManager;

public class TemplateTraitListCommand extends BrickCommand {

    public TemplateTraitListCommand() {
        super("list");

        setCondition(b -> b.permission("bricknpcs.template.trait.list"));

        ArgumentTemplate templateArg = new ArgumentTemplate("template");
        setInvalidArgumentMessage(templateArg, "cmd.error.args.template");

        setInvalidUsageMessage("cmd.template.trait.list.usage");

        addSyntax((sender, context) -> {
            NPCTemplate template = context.get(templateArg);
            TranslationManager.get().send(sender, "cmd.template.trait.list",
                    template.name(),
                    String.join(", ", template.traits())
            );
        }, templateArg);
    }

}
