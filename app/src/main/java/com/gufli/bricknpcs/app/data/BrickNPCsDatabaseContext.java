package com.gufli.bricknpcs.app.data;

import com.gufli.bricknpcs.app.data.beans.BNPCSpawn;
import com.gufli.bricknpcs.app.data.beans.BNPCTemplate;
import com.gufli.bricknpcs.app.data.beans.BNPCTemplateTrait;
import com.gufli.bricknpcs.app.data.converters.EntityTypeConverter;
import com.gufli.bricknpcs.app.data.converters.PlayerSkinConverter;
import com.gufli.brickutils.database.context.AbstractDatabaseContext;
import com.gufli.brickutils.database.converters.ComponentConverter;
import com.gufli.brickutils.database.converters.PosConverter;
import io.ebean.config.DatabaseConfig;

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
