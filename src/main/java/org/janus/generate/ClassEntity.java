package org.janus.generate;

import com.squareup.javapoet.TypeSpec;
import jakarta.persistence.Entity;
import org.apache.commons.text.CaseUtils;

import javax.lang.model.element.Modifier;

public class ClassEntity {

    public static TypeSpec generate(String tableName) {

        return TypeSpec.classBuilder(CaseUtils.toCamelCase(tableName, true, '_') + "Entity")
                .addAnnotation(Entity.class)
                .addAnnotation(AnnotationTable.generate(tableName))

                .addField(FieldSimple.generate())

                .addModifiers(Modifier.PUBLIC)
                .build();
    }
}
