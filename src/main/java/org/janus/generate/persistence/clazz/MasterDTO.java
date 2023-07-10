package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import javax.lang.model.element.Modifier;

public class MasterDTO {

    public static TypeSpec generate(String idName) {

        FieldSpec keyField = FieldSpec.builder(String.class, idName)
                .addAnnotation(NotBlank.class)
                .addAnnotation(AnnotationSpec.builder(Size.class)
                        .addMember("min", "$L", 36)
                        .addMember("max", "$L", 36)
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

        return TypeSpec.classBuilder("MasterDTO")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addField(keyField)
                .addMethod(setKey)
                .addMethod(getKey)
                .build();
    }

}
