package org.janus.generate.persistence.method;

import com.squareup.javapoet.MethodSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.GenUtil;

import javax.lang.model.element.Modifier;

public class SetMethodSimple {

    public static MethodSpec generate(ColumnSimpleSpec columnSimpleSpec) {

        String fieldName = CaseUtils.toCamelCase(columnSimpleSpec.name(), false, '_');
        return MethodSpec.methodBuilder("set" + CaseUtils.toCamelCase(columnSimpleSpec.name(), true, '_'))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(GenUtil.getTypeField(columnSimpleSpec), columnSimpleSpec.name())
                .addStatement("this." + fieldName + " = " + fieldName)
                .build();
    }
}
