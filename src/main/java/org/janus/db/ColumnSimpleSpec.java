package org.janus.db;

public record ColumnSimpleSpec(
   String name,
   String type,
   Integer size,
   Boolean nullable
) {}
