package org.janus.generate.clazz;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

import javax.lang.model.element.Modifier;

public class MasterEntity {

    public static TypeSpec generate(String idName) {

        FieldSpec keyField = FieldSpec.builder(String.class, "id")
                .addAnnotation(Id.class)
                .addAnnotation(AnnotationSpec.builder(Column.class)
                        .addMember("name", "$S", idName.toUpperCase())
                        .addMember("length", "$L", 36)
                        .addMember("nullable", "$L", false)
                        .build())
                .addAnnotation(AnnotationSpec.builder(GenericGenerator.class)
                        .addMember("name", "$S", "sequenceUUID")
                        .addMember("strategy", "$S", "com.kadipe.demo.helper.KeyGenerator")
                        .build())
                .addAnnotation(AnnotationSpec.builder(GeneratedValue.class)
                        .addMember("generator", "$S", "sequenceUUID")
                        .build())
                .addModifiers(Modifier.PRIVATE)
                .build();

        MethodSpec setKey = MethodSpec.methodBuilder("setId")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, idName)
                .addStatement("this.id = id")
                .build();

        MethodSpec getKey = MethodSpec.methodBuilder("getId")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return this.id")
                .build();

        return TypeSpec.classBuilder("MasterEntity")
                .addAnnotation(MappedSuperclass.class)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addField(keyField)
                .addMethod(setKey)
                .addMethod(getKey)
                .build();
    }

}
