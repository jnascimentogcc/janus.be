package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import jakarta.transaction.Transactional;
import org.apache.commons.text.CaseUtils;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;

public class ClassService {

    public static TypeSpec generate(String serviceName) {

        String clazzName = CaseUtils.toCamelCase(serviceName, true, ' ') + "Service";

        FieldSpec fieldLogger = FieldSpec.builder(Logger.class, "LOGGER")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL, Modifier.STATIC)
                .initializer("$T.$L", ClassName.get(LoggerFactory.class), "getLogger(" + clazzName + ".class)")
                .build();

        TypeSpec.Builder classDTOBuilder = TypeSpec.classBuilder(clazzName)
                .addAnnotation(Service.class)
                .addAnnotation(Transactional.class)
                .addModifiers(Modifier.PUBLIC)
                .addField(fieldLogger)
                ;

        return classDTOBuilder.build();
    }
}
