package org.janus.generate;

import com.squareup.javapoet.AnnotationSpec;
import jakarta.persistence.Column;

public class AnnotationColumnSimple {

    public static AnnotationSpec generate() {

        return AnnotationSpec.builder(Column.class)
                .addMember("name", "$S", "EMAIL")
                .build();
    }
}
