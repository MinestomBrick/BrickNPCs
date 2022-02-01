package com.gufli.bricknpcs.app.config;

public class BrickNPCsConfig {

    public DatabaseConfig database;

    //

    public static class DatabaseConfig {
        public String dsn;
        public String username;
        public String password;
    }

}
