package org.janus.generate.persistence.method;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;

import javax.lang.model.element.Modifier;

public class SetDTOMethodManyToOne {

    public static MethodSpec generate(ColumnManyToOneSpec columnManyToOneSpec) {

        String fieldName = CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), false, '_');
        return MethodSpec.methodBuilder("set" + CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), true, '_') + "DTO")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get("com.kadipe.demo.user.repository", CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), true, '_') + "DTO"),
                        CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), false, '_') + "DTO")
                .addStatement("this." + CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), false, '_') +
                        "DTO = " + CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), false, '_') + "DTO")
                .build();
    }
}
