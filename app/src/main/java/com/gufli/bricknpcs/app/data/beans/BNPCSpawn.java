package com.gufli.bricknpcs.app.data.beans;

import com.gufli.bricknpcs.api.npc.NPCSpawn;
import com.gufli.bricknpcs.api.npc.NPCTemplate;
import com.gufli.brickutils.database.converters.PosConverter;
import io.ebean.annotation.ConstraintMode;
import io.ebean.annotation.DbForeignKey;
import io.ebean.annotation.Index;
import net.minestom.server.coordinate.Pos;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Index(columnNames = { "name" }, unique = true)
@Table(name = "npc_spawns")
public class BNPCSpawn extends BModel implements NPCSpawn {

    @Id
    private UUID id;

    private String name;

    @ManyToOne
    @DbForeignKey(onDelete = ConstraintMode.CASCADE)
    public BNPCTemplate template;

    @Convert(converter = PosConverter.class, attributeName = "position")
    public Pos position;

    public BNPCSpawn(@NotNull String name, @NotNull Pos position, @NotNull BNPCTemplate template) {
        this.name = name;
        this.position = position;
        this.template = template;
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull NPCTemplate template() {
        return template;
    }

    @Override
    public void setTemplate(@NotNull NPCTemplate template) {
        this.template = (BNPCTemplate) template;
    }

    @Override
    public @NotNull Pos position() {
        return position;
    }

    @Override
    public void setPosition(@NotNull Pos position) {
        this.position = position;
    }
}
