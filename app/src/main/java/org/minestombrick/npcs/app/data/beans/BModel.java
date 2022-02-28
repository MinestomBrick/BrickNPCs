package org.minestombrick.npcs.app.data.beans;

import org.minestombrick.ebean.BaseModel;
import org.minestombrick.npcs.app.data.BrickNPCsDatabaseContext;
import io.ebean.Model;
import io.ebean.annotation.DbName;

import javax.persistence.MappedSuperclass;

@DbName(BrickNPCsDatabaseContext.DATASOURCE_NAME)
@MappedSuperclass
public class BModel extends Model implements BaseModel {

    public BModel() {
        super(BrickNPCsDatabaseContext.DATASOURCE_NAME);
    }

}