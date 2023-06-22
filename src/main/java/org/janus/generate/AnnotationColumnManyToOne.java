package org.janus.generate;

import com.squareup.javapoet.AnnotationSpec;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnSimpleSpec;

public class AnnotationColumnManyToOne {

    public static AnnotationSpec generate(ColumnManyToOneSpec columnManyToOneSpec) {

        AnnotationSpec.Builder annotationColumnBuilder = AnnotationSpec.builder(JoinColumn.class)
                .addMember("name", "$S", columnManyToOneSpec.name().toUpperCase());

        return annotationColumnBuilder.build();
    }
}
