package org.janus.generate.persistence.method;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnOneToManySpec;

import javax.lang.model.element.Modifier;
import java.util.Collection;

public class GetDTOMethodOneToMany {

    public static MethodSpec generate(ColumnOneToManySpec columnOneToManySpec) {

        return MethodSpec.methodBuilder("get" + CaseUtils.toCamelCase(columnOneToManySpec.tableName(), true, '_') + "DTOs")
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get(Collection.class),
                        ClassName.get("com.kadipe.demo.user.repository" ,
                        CaseUtils.toCamelCase(columnOneToManySpec.tableName(), true, '_') + "DTO")))
                .addStatement("return this." + CaseUtils.toCamelCase(columnOneToManySpec.tableName(), false, '_') + "DTOs")
                .build();
    }

}
