package org.janus.config.model;

import java.util.List;

public class BuzzArea {

    private String name;
    private String packageName;
    private List<BuzzProcess> buzzProcesses;

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

    public List<BuzzProcess> getBuzzProcesses() {
        return buzzProcesses;
    }

    public void setBuzzProcesses(List<BuzzProcess> buzzProcesses) {
        this.buzzProcesses = buzzProcesses;
    }
}
