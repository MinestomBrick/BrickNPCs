package com.gufli.bricknpcs.app.commands.template;

import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationManager;

import java.util.stream.Collectors;

public class TemplateListCommand extends BrickCommand {

    public TemplateListCommand() {
        super("list");

        setCondition(b -> b.permission("bricknpcs.spawn.list"));

        setDefaultExecutor((sender, context) -> {
            TranslationManager.get().send(sender, "cmd.spawn.list",
                    NPCAPI.get().spawns().stream()
                            .map(s -> s.name())
                            .collect(Collectors.joining(", "))
            );
        });
    }

}
