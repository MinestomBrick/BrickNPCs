package com.gufli.bricknpcs.app.commands.template;

import com.gufli.bricknpcs.app.commands.template.edit.TemplateEditCommand;
import com.gufli.brickutils.commands.BrickCommand;

public class TemplateCommand extends BrickCommand {

    public TemplateCommand() {
        super("template");

        setupCommandGroupDefaults();

        addSubcommand(new TemplateEditCommand());
    }
}
