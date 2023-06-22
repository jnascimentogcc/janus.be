package org.janus.generate;

import com.squareup.javapoet.FieldSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnSimpleSpec;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class FieldSimple {

    public static FieldSpec generate(ColumnSimpleSpec columnSimpleSpec) {

        Type typeField = getTypeField(columnSimpleSpec);

        return FieldSpec.builder(typeField, CaseUtils.toCamelCase(columnSimpleSpec.name(), false, '_'))
                .addAnnotation(AnnotationColumnSimple.generate(columnSimpleSpec))
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

    public static Type getTypeField(ColumnSimpleSpec columnSimpleSpec) {
        Type typeField = String.class;
        switch (columnSimpleSpec.type().toUpperCase()) {
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
