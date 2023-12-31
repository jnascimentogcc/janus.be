package org.janus.config.model;

public class Op {

    public enum Verb {
        GET, POST, PUT, DELETE
    }

    public enum Type {
        SIMPLE, LIST, FILTER, COMPLEX
    }

    private Verb verb;
    private Type type;
    private String filterField;
    private String existsField;
    private String orderBy;

    public Verb getVerb() {
        return verb;
    }

    public void setVerb(Verb verb) {
        this.verb = verb;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFilterField() {
        return filterField;
    }

    public void setFilterField(String filterField) {
        this.filterField = filterField;
    }

    public String getExistsField() {
        return existsField;
    }

    public void setExistsField(String existsField) {
        this.existsField = existsField;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
