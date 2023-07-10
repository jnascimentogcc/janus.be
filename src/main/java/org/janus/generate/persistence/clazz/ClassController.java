package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.*;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.apache.commons.text.CaseUtils;
import org.janus.config.model.BuzzProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.element.Modifier;
import java.util.NoSuchElementException;

public class ClassController {

    public static TypeSpec generate(BuzzProcess serviceName, String rootPackage) {

        String clazzName = CaseUtils.toCamelCase(serviceName.getName(), true, ' ') + "Controller";

        TypeSpec.Builder classServiceBuilder = TypeSpec.classBuilder(clazzName)
                .addAnnotation(CrossOrigin.class)
                .addAnnotation(RestController.class)
                .addAnnotation(AnnotationSpec.builder(RequestMapping.class)
                        .addMember("value", "$S", "/" + serviceName.getPackageName().substring(1))
                        .build())
                .addModifiers(Modifier.PUBLIC);

        FieldSpec fieldRepository = FieldSpec.builder(ClassName.get(rootPackage + serviceName.getPackageName() + ".service",
                                CaseUtils.toCamelCase(serviceName.getName(), true, '_') + "Service"),
                        CaseUtils.toCamelCase(serviceName.getName(), false, '_') + "Service")
                .addModifiers(Modifier.PRIVATE)
                .addAnnotation(Autowired.class)
                .build();
        classServiceBuilder.addField(fieldRepository);

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
                .addStatement("$T.copyProperties(" + CaseUtils.toCamelCase(tableName, false, '_') +
                        "Entity, " + CaseUtils.toCamelCase(tableName, false, '_') + "DTO)", BeanUtils.class)
                .addStatement("return " + CaseUtils.toCamelCase(tableName, false, '_') + "DTO")
                .nextControlFlow("catch($T ex)", NoSuchElementException.class)
                .addStatement("LOGGER.info(\"" + CaseUtils.toCamelCase(tableName, true, '_') + " with id: \" + id + \" not found\")")
                .addStatement("throw new $T$L", ClassName.get(rootPackage + ".helper.exception", "ItemNotFoundException"),
                        "(\"" + CaseUtils.toCamelCase(tableName, true, '_') + " with id: \" + id + \" not found\")")
                .endControlFlow()
                .build();
    }
}
