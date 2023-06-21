package org.janus.generate;

import com.squareup.javapoet.FieldSpec;

import javax.lang.model.element.Modifier;

public class FieldSimple {

    public static FieldSpec generate() {

        return FieldSpec.builder(String.class, "email")
                .addAnnotation(AnnotationColumnSimple.generate())
                .addModifiers(Modifier.PRIVATE)
                .build();
    }
}
