package org.janus.generate.method;

import com.squareup.javapoet.MethodSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.field.FieldSimple;

import javax.lang.model.element.Modifier;

public class SetMethodSimple {

    public static MethodSpec generate(ColumnSimpleSpec columnSimpleSpec) {

        String fieldName = CaseUtils.toCamelCase(columnSimpleSpec.name(), false, '_');
        return MethodSpec.methodBuilder("set" + CaseUtils.toCamelCase(columnSimpleSpec.name(), true, '_'))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(FieldSimple.getTypeField(columnSimpleSpec), columnSimpleSpec.name())
                .addStatement("this." + fieldName + " = " + fieldName)
                .build();
    }
}
