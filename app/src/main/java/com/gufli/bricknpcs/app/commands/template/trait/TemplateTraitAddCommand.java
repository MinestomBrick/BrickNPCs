package com.gufli.bricknpcs.app.commands.template.trait;

import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.bricknpcs.api.trait.TraitRegistry;
import com.gufli.bricknpcs.app.commands.arguments.ArgumentTemplate;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationManager;
import net.minestom.server.command.builder.arguments.ArgumentWord;

public class TemplateTraitAddCommand extends BrickCommand {

    public TemplateTraitAddCommand() {
        super("add");

        setCondition(b -> b.permission("bricknpcs.template.trait.add"));

        ArgumentTemplate templateArg = new ArgumentTemplate("template");
        setInvalidArgumentMessage(templateArg, "cmd.error.args.template");

        ArgumentWord traitArg = new ArgumentWord("trait").from(TraitRegistry.traits().toArray(new String[0]));
        setInvalidArgumentMessage(traitArg, "cmd.error.args.trait");

        setInvalidUsageMessage("cmd.template.trait.add.usage");

        addSyntax((sender, context) -> {
            NPCTemplate template = context.get(templateArg);
            String trait = context.get(traitArg);

            template.addTrait(trait);
            NPCAPI.get().saveTemplate(template);

            TranslationManager.get().send(sender, "cmd.template.trait.add", template.name(), trait);
        }, templateArg, traitArg);
    }

}
