package org.janus.generate.iface;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeVariableName;
import org.apache.commons.text.CaseUtils;

import javax.lang.model.element.Modifier;
import java.util.Optional;

public class MethodSignatureFindBy {

    public static MethodSpec generate(TypeVariableName entityName, String fieldName) {

        return MethodSpec.methodBuilder("findBy" + CaseUtils.toCamelCase(fieldName, true, '_'))
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addParameter(String.class, CaseUtils.toCamelCase(fieldName, false, '_'))
                .returns(ParameterizedTypeName.get(ClassName.get(Optional.class), entityName))
                .build();
    }
}
