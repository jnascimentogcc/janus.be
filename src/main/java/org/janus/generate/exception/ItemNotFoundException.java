package org.janus.generate.exception;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

public class ItemNotFoundException {

    public static TypeSpec generate() {

        return TypeSpec.classBuilder("ItemNotFoundException")
                .superclass(ClassName.get(RuntimeException.class))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(String.class, "message")
                        .addStatement("super(message)")
                        .build())
                .build();
    }
}
