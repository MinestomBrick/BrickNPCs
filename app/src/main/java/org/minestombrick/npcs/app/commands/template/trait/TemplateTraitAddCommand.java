package org.minestombrick.npcs.app.commands.template.trait;

import net.minestom.server.command.builder.arguments.ArgumentWord;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.npcs.api.NPCAPI;
import org.minestombrick.npcs.api.npc.NPCTemplate;
import org.minestombrick.npcs.api.trait.TraitRegistry;
import org.minestombrick.npcs.app.commands.arguments.ArgumentTemplate;

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

            I18nAPI.get(this).send(sender, "cmd.template.trait.add", template.name(), trait);
        }, templateArg, traitArg);
    }

}
