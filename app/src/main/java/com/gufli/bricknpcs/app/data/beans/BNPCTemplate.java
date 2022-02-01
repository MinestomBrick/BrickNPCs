package com.gufli.bricknpcs.app.data.beans;

import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.bricknpcs.app.data.converters.EntityTypeConverter;
import com.gufli.bricknpcs.app.data.converters.PlayerSkinConverter;
import com.gufli.brickutils.database.converters.ComponentConverter;
import io.ebean.annotation.Index;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.PlayerSkin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Index(columnNames = {"name"}, unique = true)
@Table(name = "npc_templates")
public class BNPCTemplate extends BModel implements NPCTemplate {

    @Id
    private UUID id;

    private final String name;

    @Convert(converter = ComponentConverter.class, attributeName = "customName")
    private Component customName;

    @Convert(converter = EntityTypeConverter.class, attributeName = "type")
    private EntityType type;

    @Convert(converter = PlayerSkinConverter.class, attributeName = "skin")
    @Column(length = 2048)
    private PlayerSkin skin;

    @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
    private final List<BNPCTemplateTrait> traits = new ArrayList<>();

    public BNPCTemplate(String name) {
        this.name = name;
    }

    //

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @Nullable Component customName() {
        return customName;
    }

    @Override
    public void setCustomName(@NotNull Component name) {
        this.customName = name;
    }

    @Override
    public @NotNull EntityType type() {
        return type;
    }

    @Override
    public void setType(@NotNull EntityType type) {
        this.type = type;
    }

    @Override
    public @Nullable PlayerSkin skin() {
        if (type != EntityType.PLAYER) throw new UnsupportedOperationException();
        return skin;
    }

    @Override
    public void setSkin(@NotNull PlayerSkin skin) {
        if (type != EntityType.PLAYER) throw new UnsupportedOperationException();
        this.skin = skin;
    }

    @Override
    public void addTrait(@NotNull String name) {
        if (traits.stream().anyMatch(trait -> trait.name().equals(name))) {
            return;
        }
        traits.add(new BNPCTemplateTrait(this, name));
    }

    @Override
    public void removeTrait(@NotNull String name) {
        Optional<BNPCTemplateTrait> trait = traits.stream().filter(t -> t.name().equals(name)).findFirst();
        if (trait.isEmpty()) {
            return;
        }

        traits.remove(trait.get());
    }

    @Override
    public @NotNull Collection<String> traits() {
        return traits.stream().map(BNPCTemplateTrait::name).collect(Collectors.toSet());
    }
}
