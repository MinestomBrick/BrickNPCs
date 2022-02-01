package com.gufli.bricknpcs.app.commands.npc;

import com.gufli.brickutils.commands.BrickCommand;

public class NPCCommand extends BrickCommand {

    public NPCCommand() {
        super("npc");

        setupCommandGroupDefaults();

        addSubcommand(new NPCCreateCommand());
    }
}
