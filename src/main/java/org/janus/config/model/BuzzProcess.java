package org.janus.config.model;

import java.util.List;

public class BuzzProcess {

    private String name;
    private String packageName;
    private List<Crud> cruds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<Crud> getCruds() {
        return cruds;
    }

    public void setCruds(List<Crud> cruds) {
        this.cruds = cruds;
    }
}
