package org.janus.generate.persistence.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.generate.persistence.annotation.AnnotationJoinColumn;
import org.janus.generate.persistence.annotation.AnnotationManyToOne;

import javax.lang.model.element.Modifier;

public class FieldDTOManyToOne {

    public static FieldSpec generate(ColumnManyToOneSpec columnManyToOneSpec) {

        return FieldSpec.builder(ClassName.get("com.kadipe.demo.user.repository",
                        CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), true, '_') + "DTO"),
                        CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), false, '_') + "DTO")
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

}
