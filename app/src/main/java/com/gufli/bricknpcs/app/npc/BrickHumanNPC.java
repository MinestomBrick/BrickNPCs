package com.gufli.bricknpcs.app.npc;

import com.gufli.bricknpcs.api.npc.NPCSpawn;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.bricknpcs.api.trait.Trait;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.fakeplayer.FakePlayer;
import net.minestom.server.entity.fakeplayer.FakePlayerOption;
import net.minestom.server.entity.hologram.Hologram;
import net.minestom.server.entity.metadata.other.ArmorStandMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.scoreboard.Team;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class BrickHumanNPC extends BrickNPC {

    private Player player;

    private final Hologram nameHologram;
    private final Entity nametagHider;

    public BrickHumanNPC(Instance instance, NPCSpawn spawn) {
        super(spawn);

        FakePlayerOption option = new FakePlayerOption();
        option.setInTabList(false);
        option.setRegistered(false);

        this.nameHologram = new Hologram(instance, spawn.position().add(0, 1.8, 0), spawn.template().customName());

        this.nametagHider = new Entity(EntityType.ARMOR_STAND);
        ArmorStandMeta armorStandMeta = (ArmorStandMeta) this.nametagHider.getEntityMeta();
        armorStandMeta.setNotifyAboutChanges(false);
        armorStandMeta.setSmall(true);
        armorStandMeta.setInvisible(true);
        this.nametagHider.setInstance(instance, spawn.position());
        this.nametagHider.setAutoViewable(true);

        FakePlayer.initPlayer(UUID.randomUUID(), RandomStringUtils.randomAlphanumeric(16), player -> {
            entity = this.player = player;
            entity.setAutoViewable(true);

            System.out.println("oi");
            player.addPassenger(nametagHider);
            refresh();

            traits.forEach(Trait::onSpawn);
        });
    }

    @Override
    public void remove() {
        super.remove();

        nameHologram.remove();
    }

    @Override
    public void refresh() {
        super.refresh();

        NPCTemplate template = spawn.template();

        if (template.customName() != null) {
            nameHologram.setText(template.customName());
        }

        if (template.skin() != null) {
            player.setSkin(template.skin());
        }
    }
}
