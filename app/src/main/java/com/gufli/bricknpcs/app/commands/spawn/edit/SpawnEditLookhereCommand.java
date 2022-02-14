package com.gufli.bricknpcs.app.commands.spawn.edit;

import com.gufli.bricknpcs.api.NPCAPI;
import com.gufli.bricknpcs.api.npc.NPCSpawn;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.bricknpcs.app.commands.arguments.ArgumentSpawn;
import com.gufli.bricknpcs.app.commands.arguments.ArgumentTemplate;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationAPI;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.utils.position.PositionUtils;

public class SpawnEditLookhereCommand extends BrickCommand {

    public SpawnEditLookhereCommand() {
        super("lookhere");

        setCondition(b -> b.permission("bricknpcs.spawn.edit.lookhere").playerOnly());

        ArgumentSpawn spawnArg = new ArgumentSpawn("spawn");
        setInvalidArgumentMessage(spawnArg, "cmd.error.args.spawn");

        setInvalidUsageMessage("cmd.spawn.edit.lookhere.usage");

        addSyntax((sender, context) -> {
            NPCSpawn spawn = context.get(spawnArg);

            Pos playerpos = ((Player) sender).getPosition().add(0, 1.6, 0);
            Pos entitypos = spawn.position().add(0, spawn.template().type().height() * 0.85, 0);
            Vec delta = playerpos.sub(entitypos).asVec().normalize();

            Pos result = spawn.position();
            result = result.withYaw(PositionUtils.getLookYaw(delta.x(), delta.z()));
            result = result.withPitch(PositionUtils.getLookPitch(delta.x(), delta.y(), delta.z()));
            spawn.setPosition(result);

            NPCAPI.get().saveSpawn(spawn);

            TranslationAPI.get().send(sender, "cmd.spawn.edit.lookhere", spawn.name());
        }, spawnArg);
    }
}
