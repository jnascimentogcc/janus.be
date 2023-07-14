package org.janus.generate.persistence.iface;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeVariableName;
import org.apache.commons.text.CaseUtils;

import javax.lang.model.element.Modifier;
import java.util.Collection;
import java.util.Optional;

public class MethodSignatureFindAllSortable {

    public static MethodSpec generate(TypeVariableName entityName, String fieldName) {

        return MethodSpec.methodBuilder("findAllByOrderBy" + CaseUtils.toCamelCase(fieldName, true)
                )
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .returns(ParameterizedTypeName.get(ClassName.get(Collection.class), entityName))
                .build();
    }
}
