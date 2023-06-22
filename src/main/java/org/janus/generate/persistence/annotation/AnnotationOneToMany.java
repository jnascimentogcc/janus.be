package org.janus.generate.persistence.annotation;

import com.squareup.javapoet.AnnotationSpec;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnOneToManySpec;

public class AnnotationOneToMany {

    public static AnnotationSpec generate(ColumnOneToManySpec columnOneToManySpec) {

        AnnotationSpec.Builder annotationColumnBuilder = AnnotationSpec.builder(OneToMany.class)
                .addMember("mappedBy", "$S", CaseUtils.toCamelCase(columnOneToManySpec.refTable(), false, '_') + "Entity")
                .addMember("fetch", "$L", FetchType.LAZY);

        return annotationColumnBuilder.build();
    }
}

