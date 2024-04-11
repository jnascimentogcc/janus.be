package org.janus.generate.persistence.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;

import javax.lang.model.element.Modifier;

public class FieldDTOManyToOne {

    public static FieldSpec generate(ColumnManyToOneSpec columnManyToOneSpec, String rootPack) {

        return FieldSpec.builder(ClassName.get(rootPack + "." + columnManyToOneSpec.pack() + ".dto",
                        CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), true, '_') + "DTO"),
                        CaseUtils.toCamelCase(columnManyToOneSpec.refTable(), false, '_') + "DTO")
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

}
