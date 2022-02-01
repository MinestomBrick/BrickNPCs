package com.gufli.bricknpcs.app.data.beans;

import com.gufli.bricknpcs.app.data.BrickNPCsDatabaseContext;
import com.gufli.brickutils.database.BaseModel;
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