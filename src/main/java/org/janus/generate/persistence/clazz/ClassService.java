package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.*;
import jakarta.transaction.Transactional;
import org.apache.commons.text.CaseUtils;
import org.janus.config.model.BuzzProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.element.Modifier;
import java.util.Collection;
import java.util.NoSuchElementException;

public class ClassService {

    public static TypeSpec generate(BuzzProcess serviceName, String rootPackage) {

        String clazzName = CaseUtils.toCamelCase(serviceName.getName(), true, ' ') + "Service";

        FieldSpec fieldLogger = FieldSpec.builder(Logger.class, "LOGGER")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL, Modifier.STATIC)
                .initializer("$T.$L", ClassName.get(LoggerFactory.class), "getLogger(" + clazzName + ".class)")
                .build();

        TypeSpec.Builder classServiceBuilder = TypeSpec.classBuilder(clazzName)
                .addAnnotation(Service.class)
                .addAnnotation(Transactional.class)
                .addModifiers(Modifier.PUBLIC)
                .addField(fieldLogger);

        serviceName.getCruds().forEach((itemCRUD) -> {
            FieldSpec fieldRepository = FieldSpec.builder(ClassName.get(rootPackage + serviceName.getPackageName() + ".repository",
                                    CaseUtils.toCamelCase(itemCRUD.getTable(), true, '_') + "Repository"),
                            CaseUtils.toCamelCase(itemCRUD.getTable(), false, '_') + "Repository")
                    .addModifiers(Modifier.PRIVATE)
                    .addAnnotation(Autowired.class)
                    .build();
            classServiceBuilder.addField(fieldRepository);
            itemCRUD.getOps().forEach((itemOP) -> {
                MethodSpec methodSpec = null;
                switch (itemOP.getVerb().name()) {
                    case "GET":
                        switch (itemOP.getType().name()) {
                            case "SIMPLE":
                                methodSpec = generateGetSimple(itemCRUD.getTable(), rootPackage, serviceName.getPackageName());
                                break;
                            case "LIST":
                                methodSpec = generateGetList(itemCRUD.getTable(), rootPackage, serviceName.getPackageName());
                                break;
                            default:
                                break;
                        }
                    default:
                        break;
                }
                if (methodSpec != null) {
                    classServiceBuilder.addMethod(methodSpec);
                }
            });
        });

        return classServiceBuilder.build();
    }

    private static MethodSpec generateGetList(String tableName, String rootPackage, String packageName) {

        return MethodSpec.methodBuilder("list" + CaseUtils.toCamelCase(tableName, true, '_'))
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get(Collection.class), ClassName.get(rootPackage + packageName + ".model", CaseUtils.toCamelCase(tableName, true, '_') + "DTO")))
                .build();
    }

    private static MethodSpec generateGetSimple(String tableName, String rootPackage, String packageName) {

        return MethodSpec.methodBuilder("get" + CaseUtils.toCamelCase(tableName, true, '_'))
                .addStatement(CaseUtils.toCamelCase(tableName, true, '_') + "DTO " +
                        CaseUtils.toCamelCase(tableName, false, '_') + "DTO = new " +
                        CaseUtils.toCamelCase(tableName, true, '_') + "DTO()")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "id")
                .returns(ClassName.get(rootPackage + packageName + ".model", CaseUtils.toCamelCase(tableName, true, '_') + "DTO"))
                .beginControlFlow("try")
                .addStatement(CaseUtils.toCamelCase(tableName, true, '_') + "Entity " +
                        CaseUtils.toCamelCase(tableName, false, '_') + "Entity = " +
                        CaseUtils.toCamelCase(tableName, false, '_') + "Repository.findById(id).orElseThrow()")
                .addStatement("$T.copyProperties(" + CaseUtils.toCamelCase(tableName, false, '_') +
                        "Entity, " + CaseUtils.toCamelCase(tableName, false, '_') + "DTO)", BeanUtils.class)
                .addStatement("return " + CaseUtils.toCamelCase(tableName, false, '_') + "DTO")
                .nextControlFlow("catch($T ex)", NoSuchElementException.class)
                .addStatement("LOGGER.info(String.format(\"" +
                        CaseUtils.toCamelCase(tableName, true, '_') +
                        " with id: %s not found.\", id))")
                .addStatement("throw new $T$L", ClassName.get(rootPackage + ".helper.exception", "ItemNotFoundException"),
                        "(String.format(\"" + CaseUtils.toCamelCase(tableName, true, '_') +
                                " with id: %s not found.\", id))")
                .endControlFlow()
                .build();
    }
}
