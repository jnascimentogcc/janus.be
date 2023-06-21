package org.janus.generate;

import com.squareup.javapoet.AnnotationSpec;
import jakarta.persistence.Table;

public class AnnotationTable {

    public static AnnotationSpec generate(String tableName) {

        return AnnotationSpec.builder(Table.class)
                .addMember("name", "$S", tableName.toUpperCase())
                .build();
    }
}
