package com.gufli.bricknpcs.app.commands.template.edit;

import com.gufli.brickutils.commands.BrickCommand;

public class TemplateEditCommand extends BrickCommand {

    public TemplateEditCommand() {
        super("edit");

        setupCommandGroupDefaults();

        addSubcommand(new TemplateEditCustomNameCommand());
        addSubcommand(new TemplateEditSkinCommand());
    }
}
