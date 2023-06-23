package org.janus.generate.persistence.field;

import com.squareup.javapoet.FieldSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.persistence.annotation.AnnotationColumn;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FieldSimple {

    public static FieldSpec generate(ColumnSimpleSpec columnSimpleSpec) {

        Type typeField = getTypeField(columnSimpleSpec);

        return FieldSpec.builder(typeField, CaseUtils.toCamelCase(columnSimpleSpec.name(), false, '_'))
                .addAnnotation(AnnotationColumn.generate(columnSimpleSpec))
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

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
