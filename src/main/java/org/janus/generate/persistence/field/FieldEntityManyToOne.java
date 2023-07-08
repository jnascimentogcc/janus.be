package org.janus.generate.persistence.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.generate.persistence.annotation.AnnotationJoinColumn;
import org.janus.generate.persistence.annotation.AnnotationManyToOne;

import javax.lang.model.element.Modifier;

public class FieldEntityManyToOne {

    public static FieldSpec generate(ColumnManyToOneSpec columnManyToOneSpec, String pack) {

        return FieldSpec.builder(ClassName.get(pack + ".repository",
                        CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), true, '_') + "Entity"),
                        CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), false, '_') + "Entity")
                .addAnnotation(AnnotationManyToOne.generate(columnManyToOneSpec))
                .addAnnotation(AnnotationJoinColumn.generate(columnManyToOneSpec))
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

}
