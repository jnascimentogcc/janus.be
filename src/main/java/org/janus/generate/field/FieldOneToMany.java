package org.janus.generate.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnOneToManySpec;
import org.janus.generate.annotation.AnnotationJoinColumn;
import org.janus.generate.annotation.AnnotationManyToOne;
import org.janus.generate.annotation.AnnotationOneToMany;

import javax.lang.model.element.Modifier;
import java.util.Collection;

public class FieldOneToMany {

    public static FieldSpec generate(ColumnOneToManySpec columnOneToManySpec) {

        return FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(Collection.class),
                                ClassName.get("com.kadipe.demo.user.repository",
                        CaseUtils.toCamelCase(columnOneToManySpec.tableName(), true, '_') + "Entity")),
                        CaseUtils.toCamelCase(columnOneToManySpec.tableName(), false, '_') + "Entities")
                .addAnnotation(AnnotationOneToMany.generate(columnOneToManySpec))
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

}
