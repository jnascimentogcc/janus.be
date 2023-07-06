package org.janus.config.model;

import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnOneToManySpec;
import org.janus.db.ColumnSimpleSpec;

import java.util.List;

public class Crud {

    private String table;
    private List<ColumnSimpleSpec> columns;
    private List<ColumnOneToManySpec> onetomany;
    private List<ColumnManyToOneSpec> manytoone;
    private List<Op> ops;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<ColumnSimpleSpec> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnSimpleSpec> columns) {
        this.columns = columns;
    }

    public List<ColumnOneToManySpec> getOnetomany() {
        return onetomany;
    }

    public void setOnetomany(List<ColumnOneToManySpec> onetomany) {
        this.onetomany = onetomany;
    }

    public List<ColumnManyToOneSpec> getManytoone() {
        return manytoone;
    }

    public void setManytoone(List<ColumnManyToOneSpec> manytoone) {
        this.manytoone = manytoone;
    }

    public List<Op> getOps() {
        return ops;
    }

    public void setOps(List<Op> ops) {
        this.ops = ops;
    }
}
