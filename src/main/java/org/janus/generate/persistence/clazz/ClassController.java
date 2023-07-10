package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.*;
import org.apache.commons.text.CaseUtils;
import org.janus.config.model.BuzzProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.Modifier;

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
                                        .addMethod(generateGetSimple(itemCRUD.getTable(),
                                                CaseUtils.toCamelCase(serviceName.getName(), false, '_') +
                                                        "Service", rootPackage, serviceName.getPackageName()));
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

    private static MethodSpec generateGetSimple(String tableName, String serviceName, String rootPackage, String packageName) {

        return MethodSpec.methodBuilder("get" + CaseUtils.toCamelCase(tableName, true, '_'))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(GetMapping.class)
                        .addMember("value", "$S", "${apiprefix.v1}/" +
                                tableName.replace("_", "").toLowerCase() + "/{id}")
                        .build())
                .returns(ClassName.get(rootPackage + packageName + ".model",
                        CaseUtils.toCamelCase(tableName, true, '_') + "DTO"))
                .addParameter(ParameterSpec.builder(String.class, "id")
                        .addAnnotation(PathVariable.class)
                        .build())
                .addStatement("return " + serviceName + ".get" +
                        CaseUtils.toCamelCase(tableName, true, '_') + "(id)")
                .build();
    }
}
