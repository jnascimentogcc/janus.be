package org.janus.generate.annotation;

import com.squareup.javapoet.AnnotationSpec;
import jakarta.persistence.JoinColumn;
import org.janus.db.ColumnManyToOneSpec;

public class AnnotationJoinColumn {

    public static AnnotationSpec generate(ColumnManyToOneSpec columnManyToOneSpec) {

        AnnotationSpec.Builder annotationColumnBuilder = AnnotationSpec.builder(JoinColumn.class)
                .addMember("name", "$S", columnManyToOneSpec.name().toUpperCase());
        if (!columnManyToOneSpec.nullable()) {
            annotationColumnBuilder.addMember("nullable", "$L", Boolean.FALSE);
        }

        return annotationColumnBuilder.build();
    }
}
