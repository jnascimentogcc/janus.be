package org.janus.generate.annotation;

import com.squareup.javapoet.AnnotationSpec;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.janus.db.ColumnManyToOneSpec;

public class AnnotationManyToOne {

    public static AnnotationSpec generate(ColumnManyToOneSpec columnManyToOneSpec) {

        AnnotationSpec.Builder annotationColumnBuilder = AnnotationSpec.builder(ManyToOne.class)
                .addMember("fetch", "$L", FetchType.LAZY);
        if (!columnManyToOneSpec.nullable()) {
            annotationColumnBuilder.addMember("optional", "$L", Boolean.FALSE);
        }

        return annotationColumnBuilder.build();
    }
}

