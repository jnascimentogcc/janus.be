package org.janus.generate.iface;

import com.squareup.javapoet.MethodSpec;
import org.apache.commons.text.CaseUtils;

import javax.lang.model.element.Modifier;

public class MethodSignatureExists {

    public static MethodSpec generate(String fieldName) {

        return MethodSpec.methodBuilder("existsBy" + CaseUtils.toCamelCase(fieldName, true, '_'))
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addParameter(String.class, CaseUtils.toCamelCase(fieldName, false, '_'))
                .returns(Boolean.class)
                .build();
    }
}
