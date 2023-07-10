package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.lang.model.element.Modifier;

public class ClassApplication {

    public static TypeSpec generate(String rootPackage) {

        MethodSpec mainMethod = MethodSpec.methodBuilder("main")
                .addParameter(String.class, "args")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addStatement("SpringApplication.run(Application.class, args)")
                .build();

        return TypeSpec.classBuilder("Application")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(SpringBootApplication.class)
                .addAnnotation(AnnotationSpec.builder(EntityScan.class)
                        .addMember("basePackages", "$S", rootPackage)
                        .build())
                .addAnnotation(AnnotationSpec.builder(ComponentScan.class)
                        .addMember("basePackages", "$S", rootPackage)
                        .build())
                .addAnnotation(AnnotationSpec.builder(ServletComponentScan.class)
                        .addMember("basePackages", "$S", rootPackage)
                        .build())
                .addAnnotation(EnableScheduling.class)
                .addAnnotation(EnableCaching.class)
                .addMethod(mainMethod)
                .build();
    }

}
