package org.janus.generate.iface;

import com.squareup.javapoet.*;
import org.apache.commons.text.CaseUtils;
import org.springframework.data.repository.CrudRepository;

import javax.lang.model.element.Modifier;
import java.util.Optional;

public class InterfaceRepository {

    public static TypeSpec generate(String tableName) {

        String prefixName = CaseUtils.toCamelCase(tableName, true, '_');
        TypeVariableName entity = TypeVariableName.get(prefixName + "Entity");
        TypeVariableName idType = TypeVariableName.get("String").withBounds(String.class);

        return TypeSpec
                .interfaceBuilder(prefixName + "Repository")
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(CrudRepository.class), entity, idType))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("findByEmail")
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .addParameter(String.class, "email")
                        .returns(ParameterizedTypeName.get(ClassName.get(Optional.class), entity))
                        .build())
                .build();
    }
}
