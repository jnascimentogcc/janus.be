package org.janus.db;

public record ColumnManyToOneSpec(
   String name,
   String refTable,
   Boolean nullable
) {}
