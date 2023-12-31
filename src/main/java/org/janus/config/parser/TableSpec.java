package org.janus.config.parser;

import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnOneToManySpec;
import org.janus.db.ColumnSimpleSpec;

import java.util.List;

public record TableSpec(
        String name,
        String pack,
        List<ColumnSimpleSpec> columns,
        List<ColumnOneToManySpec> onetomany,
        List<ColumnManyToOneSpec> manytoone) {}
