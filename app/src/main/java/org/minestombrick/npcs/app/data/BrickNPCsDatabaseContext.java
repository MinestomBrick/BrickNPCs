package org.minestombrick.npcs.app.data;

import io.ebean.config.DatabaseConfig;
import org.minestombrick.ebean.context.AbstractDatabaseContext;
import org.minestombrick.ebean.converters.ComponentConverter;
import org.minestombrick.ebean.converters.PosConverter;
import org.minestombrick.npcs.app.data.beans.BNPCSpawn;
import org.minestombrick.npcs.app.data.beans.BNPCTemplate;
import org.minestombrick.npcs.app.data.beans.BNPCTemplateTrait;
import org.minestombrick.npcs.app.data.converters.EntityTypeConverter;
import org.minestombrick.npcs.app.data.converters.PlayerSkinConverter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BrickNPCsDatabaseContext extends AbstractDatabaseContext {

    public final static String DATASOURCE_NAME = "BrickNPC";

    public BrickNPCsDatabaseContext() {
        super(DATASOURCE_NAME);
    }

    @Override
    protected void buildConfig(DatabaseConfig config) {
        classes().forEach(config::addClass);
    }
    
    public static Collection<Class<?>> classes() {
        Set<Class<?>> classes = new HashSet<>();

        // register converters
        classes.add(EntityTypeConverter.class);
        classes.add(PlayerSkinConverter.class);
        classes.add(PosConverter.class);
        classes.add(ComponentConverter.class);

        // beans
        classes.add(BNPCSpawn.class);
        classes.add(BNPCTemplate.class);
        classes.add(BNPCTemplateTrait.class);

        return classes;
    }
    

}
