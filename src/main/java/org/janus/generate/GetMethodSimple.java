package org.janus.generate;

import com.squareup.javapoet.MethodSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnSpec;

import javax.lang.model.element.Modifier;

public class GetMethodSimple {

    public static MethodSpec generate(ColumnSpec columnSpec) {

        return MethodSpec.methodBuilder("get" + CaseUtils.toCamelCase(columnSpec.name(), true, '_'))
                .addModifiers(Modifier.PUBLIC)
                .returns(FieldSimple.getTypeField(columnSpec))
                .addStatement("return this." + CaseUtils.toCamelCase(columnSpec.name(), false, '_'))
                .build();
    }

}
