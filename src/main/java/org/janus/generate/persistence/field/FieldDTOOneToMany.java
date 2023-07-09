package org.janus.generate.persistence.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnOneToManySpec;

import javax.lang.model.element.Modifier;
import java.util.Collection;

public class FieldDTOOneToMany {

    public static FieldSpec generate(ColumnOneToManySpec columnOneToManySpec, String pack) {

        return FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(Collection.class),
                                ClassName.get(pack + ".model",
                        CaseUtils.toCamelCase(columnOneToManySpec.tableName(), true, '_') + "DTO")),
                        CaseUtils.toCamelCase(columnOneToManySpec.tableName(), false, '_') + "DTOs")
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

}
