package org.janus.generate;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import org.apache.commons.text.CaseUtils;
import org.springframework.data.repository.CrudRepository;

import javax.lang.model.element.Modifier;

public class InterfaceRepository {

    public static TypeSpec generate(String tableName) {

        String prefixName = CaseUtils.toCamelCase(tableName, true, '_');
        TypeVariableName entity = TypeVariableName.get(prefixName + "Entity");
        TypeVariableName idType = TypeVariableName.get("String").withBounds(String.class);

        return TypeSpec
                .interfaceBuilder(prefixName + "Repository")
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(CrudRepository.class), entity, idType))
                .addModifiers(Modifier.PUBLIC)
                .build();
    }
}
