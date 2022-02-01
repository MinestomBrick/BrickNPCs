package com.gufli.bricknpcs.app.commands.spawn.edit;

import com.gufli.brickutils.commands.BrickCommand;

public class SpawnEditCommand extends BrickCommand {

    public SpawnEditCommand() {
        super("edit");

        setupCommandGroupDefaults();

        addSubcommand(new SpawnEditLookhereCommand());
    }
}
