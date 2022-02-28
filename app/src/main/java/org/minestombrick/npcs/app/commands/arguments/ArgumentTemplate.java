package org.minestombrick.npcs.app.commands.arguments;

import org.minestombrick.npcs.api.NPCAPI;
import org.minestombrick.npcs.api.npc.NPCTemplate;
import net.minestom.server.command.builder.NodeMaker;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArgumentTemplate extends Argument<NPCTemplate> {

    public static final int TEMPLATE_NOT_EXIST = 1;

    public ArgumentTemplate(@NotNull String id) {
        super(id);
    }

    @Override
    public @NotNull NPCTemplate parse(@NotNull String input) throws ArgumentSyntaxException {
        return NPCAPI.get().template(input).orElseThrow(() ->
                new ArgumentSyntaxException("Template does not exist.", input, TEMPLATE_NOT_EXIST));
    }

    @Override
    public void processNodes(@NotNull NodeMaker nodeMaker, boolean executable) {
        List<NPCTemplate> templates = new ArrayList<>(NPCAPI.get().templates());

        // Create a primitive array for mapping
        DeclareCommandsPacket.Node[] nodes = new DeclareCommandsPacket.Node[templates.size()];

        // Create a node for each restrictions as literal
        for (int i = 0; i < nodes.length; i++) {
            DeclareCommandsPacket.Node argumentNode = new DeclareCommandsPacket.Node();

            argumentNode.flags = DeclareCommandsPacket.getFlag(DeclareCommandsPacket.NodeType.LITERAL,
                    executable, false, false);
            argumentNode.name = templates.get(i).name();
            nodes[i] = argumentNode;
        }
        nodeMaker.addNodes(nodes);
    }
}
