package org.janus.generate.clazz;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import javax.lang.model.element.Modifier;
import java.io.Serializable;

public class TableKeyHelper {

    public static TypeSpec generate() {

        MethodSpec generateMethod = MethodSpec.methodBuilder("generate")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(Serializable.class)
                .addParameter(SharedSessionContractImplementor.class, "sharedSession")
                .addParameter(Object.class, "object")
                .addStatement("return UUID.randomUUID().toString()")
                .build();

        return TypeSpec.classBuilder("KeyGenerator")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(IdentifierGenerator.class)
                .addMethod(generateMethod)
                .build();
    }
}
