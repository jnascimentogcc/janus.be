package org.janus.config.model;

import java.util.List;

public class BuzzUnit {
    private String name;
    private String packageName;
    private List<BuzzArea> buzzAreas;

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

    public List<BuzzArea> getBuzzAreas() {
        return buzzAreas;
    }

    public void setBuzzAreas(List<BuzzArea> buzzAreas) {
        this.buzzAreas = buzzAreas;
    }
}
