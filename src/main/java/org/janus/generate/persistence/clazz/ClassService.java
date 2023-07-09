package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import jakarta.transaction.Transactional;
import org.apache.commons.text.CaseUtils;
import org.janus.config.model.BuzzProcess;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
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
            itemCRUD.getOps().forEach((itemOP) -> {
                switch (itemOP.getVerb().name()) {
                    case "GET":
                        switch (itemOP.getType().name()) {
                            case "SIMPLE":
                                classServiceBuilder
                                        .addMethod(generateGetSimple(itemCRUD.getTable(), rootPackage, serviceName.getPackageName()));
                                break;
                            default:
                                break;
                        }
                    default:
                        break;
                }
            });
        });

        return classServiceBuilder.build();
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
                .addStatement("return " + CaseUtils.toCamelCase(tableName, false, '_') + "DTO")
                .nextControlFlow("catch($T ex)", NoSuchElementException.class)
                .addStatement("LOGGER.info(\"" + CaseUtils.toCamelCase(tableName, true, '_') + " with id: \" + id + \" not found\")")
                .addStatement("throw new $T$L", ClassName.get(rootPackage + ".helper.exception", "ItemNotFoundException"),
                        "(\"" + CaseUtils.toCamelCase(tableName, true, '_') + " with id: \" + id + \" not found\")")
                .endControlFlow()
                .build();
    }
}
