package org.janus.config.model;

import java.util.List;

public class Crud {

    private String table;
    private List<Op> ops;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<Op> getOps() {
        return ops;
    }

    public void setOps(List<Op> ops) {
        this.ops = ops;
    }
}
