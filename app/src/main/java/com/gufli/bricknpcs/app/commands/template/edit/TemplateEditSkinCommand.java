package com.gufli.bricknpcs.app.commands.template.edit;

import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.bricknpcs.app.commands.arguments.ArgumentTemplate;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentUUID;
import net.minestom.server.entity.PlayerSkin;

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
            } catch (Exception ignored) {}

            if (skin == null) {
                TranslationAPI.get().send(sender, "cmd.template.edit.skin.invalid", context.get(playerArg));
                return;
            }

            template.setSkin(skin);
            NPCAPI.get().saveTemplate(template);

            TranslationAPI.get().send(sender, "cmd.template.edit.skin", template.name());
        }, templateArg, playerArg);
    }
}
