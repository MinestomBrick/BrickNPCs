package org.minestombrick.npcs.app.data.beans;

import org.minestombrick.npcs.api.npc.NPCTemplate;
import io.ebean.annotation.ConstraintMode;
import io.ebean.annotation.DbForeignKey;
import io.ebean.annotation.Index;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Index(columnNames = {"template_id", "name"}, unique = true)
@Table(name = "npc_template_traits")
public class BNPCTemplateTrait extends BModel {

    @Id
    private UUID id;

    @ManyToOne
    @DbForeignKey(onDelete = ConstraintMode.CASCADE)
    private final BNPCTemplate template;

    private final String name;

    public BNPCTemplateTrait(BNPCTemplate template, String trait) {
        this.template = template;
        this.name = trait;
    }

    //

    public @NotNull UUID id() {
        return id;
    }

    public @NotNull NPCTemplate template() {
        return template;
    }

    public @NotNull String name() {
        return name;
    }

}
