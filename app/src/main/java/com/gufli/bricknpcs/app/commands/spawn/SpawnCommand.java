package com.gufli.bricknpcs.app.commands.spawn;

import com.gufli.brickutils.commands.BrickCommand;

public class SpawnCommand extends BrickCommand {

    public SpawnCommand() {
        super("spawn");

        setupCommandGroupDefaults();

        addSubcommand(new SpawnCreateCommand());
    }
}
