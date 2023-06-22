package org.janus.generate;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;

import javax.lang.model.element.Modifier;

public class FieldManyToOne {

    public static FieldSpec generate(ColumnManyToOneSpec columnManyToOneSpec) {

        return FieldSpec.builder(ClassName.get("com.kadipe.demo.user.repository",
                        CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), true, '_') + "Entity"),
                        CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), false, '_') + "Entity")
                .addAnnotation(AnnotationSpec.builder(ManyToOne.class)
                        .addMember("fetch", "$L", FetchType.LAZY)
                        .build())
                .addAnnotation(AnnotationColumnManyToOne.generate(columnManyToOneSpec))
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

}
