package com.gufli.bricknpcs.app.commands;

import com.gufli.bricknpcs.app.commands.npc.NPCCommand;
import com.gufli.bricknpcs.app.commands.spawn.SpawnCommand;
import com.gufli.bricknpcs.app.commands.template.TemplateCommand;
import com.gufli.brickutils.commands.BrickCommand;

public class BrickNPCCommand extends BrickCommand {

    public BrickNPCCommand() {
        super("bricknpcs", "bn");

        setupCommandGroupDefaults();

        addSubcommand(new NPCCommand());
        addSubcommand(new TemplateCommand());
        addSubcommand(new SpawnCommand());
    }
}
