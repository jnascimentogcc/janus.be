package org.janus.generate.persistence.method;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;

import javax.lang.model.element.Modifier;

public class GetDTOMethodManyToOne {

    public static MethodSpec generate(ColumnManyToOneSpec columnManyToOneSpec, String pack) {

        return MethodSpec.methodBuilder("get" + CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), true, '_') + "DTO")
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get(pack  + ".model" ,
                        CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), true, '_') + "DTO"))
                .addStatement("return this." + CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), false, '_') + "DTO")
                .build();
    }

}
