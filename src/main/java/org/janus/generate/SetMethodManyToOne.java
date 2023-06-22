package org.janus.generate;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnSimpleSpec;

import javax.lang.model.element.Modifier;

public class SetMethodManyToOne {

    public static MethodSpec generate(ColumnManyToOneSpec columnManyToOneSpec) {

        String fieldName = CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), false, '_');
        return MethodSpec.methodBuilder("set" + CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), true, '_') + "Entity")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get("com.kadipe.demo.user.repository", CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), true, '_') + "Entity"),
                        CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), false, '_') + "Entity")
                .addStatement("this." + CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), false, '_') +
                        "Entity = " + CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), false, '_') + "Entity")
                .build();
    }
}
