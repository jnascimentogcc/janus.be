package org.janus.generate;

import org.janus.db.ColumnSimpleSpec;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GenUtil {

    public static Type getTypeField(ColumnSimpleSpec columnSimpleSpec) {
        Type typeField = String.class;
        switch (columnSimpleSpec.type().toUpperCase()) {
            case "DECIMAL":
                typeField = BigDecimal.class;
                break;
            case "DATETIME":
                typeField = LocalDateTime.class;
                break;
            case "SMALLINT", "INT":
                typeField = Integer.class;
                break;
            default:
                break;
        }
        return typeField;
    }

}
