package org.janus.generate.method;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnOneToManySpec;

import javax.lang.model.element.Modifier;

public class SetMethodOneTOMany {

    public static MethodSpec generate(ColumnOneToManySpec columnOneToManySpec) {

        String fieldName = CaseUtils.toCamelCase(columnOneToManySpec.refTable(), false, '_');
        return MethodSpec.methodBuilder("set" + CaseUtils.toCamelCase(columnOneToManySpec.tableName(), true, '_') + "Entities")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get("com.kadipe.demo.user.repository", CaseUtils.toCamelCase(columnOneToManySpec.tableName(), true, '_') + "Entity"),
                        CaseUtils.toCamelCase(columnOneToManySpec.tableName(), false, '_') + "Entities")
                .addStatement("this." + CaseUtils.toCamelCase(columnOneToManySpec.tableName(), false, '_') +
                        "Entities = " + CaseUtils.toCamelCase(columnOneToManySpec.tableName(), false, '_') + "Entities")
                .build();
    }
}
