package com.gufli.bricknpcs.app.npc;

import com.gufli.bricknpcs.api.npc.NPCSpawn;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.bricknpcs.app.npc.player.FakeFakePlayer;
import net.minestom.server.instance.Instance;
import org.apache.commons.lang3.RandomStringUtils;

public class BrickHumanNPC extends BrickNPC {

    public BrickHumanNPC(Instance instance, NPCSpawn spawn) {
        super(spawn, new FakeFakePlayer(instance, RandomStringUtils.randomAlphanumeric(16)));
    }

    @Override
    public void refresh() {
        super.refresh();
        NPCTemplate template = spawn.template();
        FakeFakePlayer player = (FakeFakePlayer) entity;

        if (template.customName() != null) {
            player.setDisplayName(spawn.template().customName());
        }

        if (template.skin() != null) {
            player.setSkin(template.skin());
        }
    }
}
