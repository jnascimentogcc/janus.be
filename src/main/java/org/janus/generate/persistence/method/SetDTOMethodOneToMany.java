package org.janus.generate.persistence.method;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnOneToManySpec;

import javax.lang.model.element.Modifier;

public class SetDTOMethodOneToMany {

    public static MethodSpec generate(ColumnOneToManySpec columnOneToManySpec) {

        String fieldName = CaseUtils.toCamelCase(columnOneToManySpec.refTable(), false, '_');
        return MethodSpec.methodBuilder("set" + CaseUtils.toCamelCase(columnOneToManySpec.tableName(), true, '_') + "DTOs")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get("com.kadipe.demo.user.repository", CaseUtils.toCamelCase(columnOneToManySpec.tableName(), true, '_') + "DTO"),
                        CaseUtils.toCamelCase(columnOneToManySpec.tableName(), false, '_') + "DTOs")
                .addStatement("this." + CaseUtils.toCamelCase(columnOneToManySpec.tableName(), false, '_') +
                        "DTOs = " + CaseUtils.toCamelCase(columnOneToManySpec.tableName(), false, '_') + "DTOs")
                .build();
    }
}
