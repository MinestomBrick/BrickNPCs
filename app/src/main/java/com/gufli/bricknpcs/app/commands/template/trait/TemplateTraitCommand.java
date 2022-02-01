package com.gufli.bricknpcs.app.commands.template.trait;

import com.gufli.brickutils.commands.BrickCommand;

public class TemplateTraitCommand extends BrickCommand {

    public TemplateTraitCommand() {
        super("trait");

        setupCommandGroupDefaults();

        addSubcommand(new TemplateTraitListCommand());
        addSubcommand(new TemplateTraitAddCommand());
        addSubcommand(new TemplateTraitRemoveCommand());
    }
}
