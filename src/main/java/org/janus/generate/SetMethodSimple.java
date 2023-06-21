package org.janus.generate;

import com.squareup.javapoet.MethodSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnSpec;

import javax.lang.model.element.Modifier;

public class SetMethodSimple {

    public static MethodSpec generate(ColumnSpec columnSpec) {

        String fieldName = CaseUtils.toCamelCase(columnSpec.name(), false, '_');
        return MethodSpec.methodBuilder("set" + CaseUtils.toCamelCase(columnSpec.name(), true, '_'))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(FieldSimple.getTypeField(columnSpec), columnSpec.name())
                .addStatement("this." + fieldName + " = " + fieldName)
                .build();
    }
}
