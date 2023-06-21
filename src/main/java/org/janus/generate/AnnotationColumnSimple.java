package org.janus.generate;

import com.squareup.javapoet.AnnotationSpec;
import jakarta.persistence.Column;
import org.janus.db.ColumnSpec;

public class AnnotationColumnSimple {

    public static AnnotationSpec generate(ColumnSpec columnSpec) {

        AnnotationSpec.Builder annotationColumnBuilder = AnnotationSpec.builder(Column.class)
                .addMember("name", "$S", columnSpec.name().toUpperCase())
                .addMember("nullable", "$L", columnSpec.nullable());
        if ("VARCHAR" .equals(columnSpec.type().toUpperCase()) || "CHAR" .equals(columnSpec.type().toUpperCase())) {
            annotationColumnBuilder.addMember("length", "$L", columnSpec.size());
        }

        return annotationColumnBuilder.build();
    }
}
