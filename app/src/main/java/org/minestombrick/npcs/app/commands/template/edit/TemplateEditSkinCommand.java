package org.minestombrick.npcs.app.commands.template.edit;

import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.entity.PlayerSkin;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.npcs.api.NPCAPI;
import org.minestombrick.npcs.api.npc.NPCTemplate;
import org.minestombrick.npcs.app.commands.arguments.ArgumentTemplate;

public class TemplateEditSkinCommand extends BrickCommand {

    public TemplateEditSkinCommand() {
        super("skin");

        setCondition(b -> b.permission("bricknpcs.template.edit.skin"));

        ArgumentTemplate templateArg = new ArgumentTemplate("template");
        setInvalidArgumentMessage(templateArg, "cmd.error.args.template");

        ArgumentWord playerArg = new ArgumentWord("player");

        setInvalidUsageMessage("cmd.template.edit.skin.usage");

        addSyntax((sender, context) -> {
            NPCTemplate template = context.get(templateArg);

            PlayerSkin skin = null;
            try {
                skin = PlayerSkin.fromUsername(context.get(playerArg));
            } catch (Exception ignored) {
            }

            if (skin == null) {
                I18nAPI.get(this).send(sender, "cmd.template.edit.skin.invalid", context.get(playerArg));
                return;
            }

            template.setSkin(skin);
            NPCAPI.get().saveTemplate(template);

            I18nAPI.get(this).send(sender, "cmd.template.edit.skin", template.name());
        }, templateArg, playerArg);
    }
}
