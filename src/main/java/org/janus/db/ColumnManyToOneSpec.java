package org.janus.db;

public record ColumnManyToOneSpec(
   String name,
   String refTable,
   String pack,
   Boolean nullable
) {}
