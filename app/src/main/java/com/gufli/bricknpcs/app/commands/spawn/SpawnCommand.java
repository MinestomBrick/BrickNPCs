package com.gufli.bricknpcs.app.commands.spawn;

import com.gufli.bricknpcs.app.commands.spawn.edit.SpawnEditCommand;
import com.gufli.brickutils.commands.BrickCommand;

public class SpawnCommand extends BrickCommand {

    public SpawnCommand() {
        super("spawn");

        setupCommandGroupDefaults();

        addSubcommand(new SpawnCreateCommand());
        addSubcommand(new SpawnDeleteCommand());
        addSubcommand(new SpawnListCommand());
        addSubcommand(new SpawnEditCommand());
    }
}
