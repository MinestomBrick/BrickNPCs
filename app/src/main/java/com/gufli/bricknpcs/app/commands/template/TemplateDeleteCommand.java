package com.gufli.bricknpcs.app.commands.template;

import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.bricknpcs.app.commands.arguments.ArgumentTemplate;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationAPI;

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
            TranslationAPI.get().send(sender, "cmd.template.delete", template.name());
        }, templateArg);
    }

}
