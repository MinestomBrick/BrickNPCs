package com.gufli.bricknpcs.app.commands.arguments;

import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.bricknpcs.api.npc.NPCSpawn;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import net.minestom.server.command.builder.NodeMaker;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArgumentSpawn extends Argument<NPCSpawn> {

    public static final int SPAWN_NOT_EXIST = 1;

    public ArgumentSpawn(@NotNull String id) {
        super(id);
    }

    @Override
    public @NotNull NPCSpawn parse(@NotNull String input) throws ArgumentSyntaxException {
        return NPCAPI.get().spawn(input).orElseThrow(() ->
                new ArgumentSyntaxException("Spawn does not exist.", input, SPAWN_NOT_EXIST));
    }

    @Override
    public void processNodes(@NotNull NodeMaker nodeMaker, boolean executable) {
        List<NPCSpawn> spawns = new ArrayList<>(NPCAPI.get().spawns());

        // Create a primitive array for mapping
        DeclareCommandsPacket.Node[] nodes = new DeclareCommandsPacket.Node[spawns.size()];

        // Create a node for each restrictions as literal
        for (int i = 0; i < nodes.length; i++) {
            DeclareCommandsPacket.Node argumentNode = new DeclareCommandsPacket.Node();

            argumentNode.flags = DeclareCommandsPacket.getFlag(DeclareCommandsPacket.NodeType.LITERAL,
                    executable, false, false);
            argumentNode.name = spawns.get(i).name();
            nodes[i] = argumentNode;
        }
        nodeMaker.addNodes(nodes);
    }
}
