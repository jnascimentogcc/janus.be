package org.janus.generate;

import com.squareup.javapoet.FieldSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnSpec;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class FieldSimple {

    public static FieldSpec generate(ColumnSpec columnSpec) {

        Type typeField = getTypeField(columnSpec);

        return FieldSpec.builder(typeField, CaseUtils.toCamelCase(columnSpec.name(), false, '_'))
                .addAnnotation(AnnotationColumnSimple.generate(columnSpec))
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

    public static Type getTypeField(ColumnSpec columnSpec) {
        Type typeField = String.class;
        switch (columnSpec.type().toUpperCase()) {
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
