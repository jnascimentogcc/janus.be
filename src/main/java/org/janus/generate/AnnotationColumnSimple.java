package org.janus.generate;

import com.squareup.javapoet.AnnotationSpec;
import jakarta.persistence.Column;
import org.janus.db.ColumnSimpleSpec;

public class AnnotationColumnSimple {

    public static AnnotationSpec generate(ColumnSimpleSpec columnSimpleSpec) {

        AnnotationSpec.Builder annotationColumnBuilder = AnnotationSpec.builder(Column.class)
                .addMember("name", "$S", columnSimpleSpec.name().toUpperCase())
                .addMember("nullable", "$L", columnSimpleSpec.nullable());
        if ("VARCHAR" .equalsIgnoreCase(columnSimpleSpec.type()) || "CHAR" .equalsIgnoreCase(columnSimpleSpec.type())) {
            annotationColumnBuilder.addMember("length", "$L", columnSimpleSpec.size());
        }

        return annotationColumnBuilder.build();
    }
}
