package org.janus.generate.persistence.method;

import com.squareup.javapoet.MethodSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.persistence.field.FieldSimple;

import javax.lang.model.element.Modifier;

public class GetMethodSimple {

    public static MethodSpec generate(ColumnSimpleSpec columnSimpleSpec) {

        return MethodSpec.methodBuilder("get" + CaseUtils.toCamelCase(columnSimpleSpec.name(), true, '_'))
                .addModifiers(Modifier.PUBLIC)
                .returns(FieldSimple.getTypeField(columnSimpleSpec))
                .addStatement("return this." + CaseUtils.toCamelCase(columnSimpleSpec.name(), false, '_'))
                .build();
    }

}
