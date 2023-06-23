package org.janus.config.model;

import java.util.List;

public class ConfigJanus {

    private String appName;
    private String rootPackage;
    private String databaseUrl;
    private String databaseUser;
    private String databasePassword;
    private String databaseSchema;
    private List<BuzzUnit> buzzUnits;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getRootPackage() {
        return rootPackage;
    }

    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseSchema() {
        return databaseSchema;
    }

    public void setDatabaseSchema(String databaseSchema) {
        this.databaseSchema = databaseSchema;
    }

    public List<BuzzUnit> getBuzzUnits() {
        return buzzUnits;
    }

    public void setBuzzUnits(List<BuzzUnit> buzzUnits) {
        this.buzzUnits = buzzUnits;
    }
}
