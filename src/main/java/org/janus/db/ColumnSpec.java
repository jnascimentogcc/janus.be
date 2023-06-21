package org.janus.db;

public record ColumnSpec(
   String name,
   String type,
   Integer size,
   Boolean nullable,
   Boolean pk
) {}
