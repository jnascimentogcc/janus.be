package org.janus.generate.controller;

import com.squareup.javapoet.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.lang.model.element.Modifier;

public class ControllerExceptionHandler {

    public static TypeSpec generate() {

        return TypeSpec.classBuilder("ControllerExceptionHandler")
                .addAnnotation(ControllerAdvice.class)
                .superclass(ClassName.get(ResponseEntityExceptionHandler.class))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("handleNotExistException")
                        .addAnnotation(AnnotationSpec.builder(ExceptionHandler.class)
                                .addMember("value",
                                        "{$T}",
                                        ClassName.get("com.autoloan.helper.exception", "ItemNotFoundException"))
                                .build())
                        .addModifiers(Modifier.PUBLIC)
                        .returns(ParameterizedTypeName.get(ClassName.get(ResponseEntity.class),
                                TypeVariableName.get("Object")))
                        .addParameter(RuntimeException.class, "ex")
                        .addParameter(WebRequest.class, "request")
                        .addStatement("return new ResponseEntity<>(HttpStatus.NOT_FOUND);")
                        .build())
                .build();
    }
}
