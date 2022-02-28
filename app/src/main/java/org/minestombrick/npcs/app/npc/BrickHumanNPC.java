package org.minestombrick.npcs.app.npc;

import org.minestombrick.npcs.api.npc.NPCSpawn;
import org.minestombrick.npcs.api.npc.NPCTemplate;
import org.minestombrick.npcs.app.npc.player.FakeFakePlayer;
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
