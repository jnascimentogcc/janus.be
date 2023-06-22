package org.janus.db;

public record ColumnOneToManySpec(
   String tableName,
   String refTable
) {}
