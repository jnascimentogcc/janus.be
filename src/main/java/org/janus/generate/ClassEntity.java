package org.janus.generate;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import jakarta.persistence.Entity;
import org.apache.commons.text.CaseUtils;
import org.springframework.data.repository.CrudRepository;

import javax.lang.model.element.Modifier;

public class ClassEntity {

    public static TypeSpec generate(String tableName) {

        TypeVariableName masterEntity = TypeVariableName.get("MasterEntity");

        return TypeSpec.classBuilder(CaseUtils.toCamelCase(tableName, true, '_') + "Entity")
                .addAnnotation(Entity.class)
                .addAnnotation(AnnotationTable.generate(tableName))
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(MasterEntity.class)))

                .addField(FieldSimple.generate())

                .addModifiers(Modifier.PUBLIC)
                .build();
    }
}
