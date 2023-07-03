package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import jakarta.persistence.Entity;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnOneToManySpec;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.persistence.annotation.AnnotationTable;

import javax.lang.model.element.Modifier;
import java.util.List;

public class ClassDTO {

    public static TypeSpec generate(String tableName, List<ColumnSimpleSpec> listColumn, List<ColumnManyToOneSpec> listColumnManyToOne, List<ColumnOneToManySpec> listColumnOneToMany) {

        String dtoName = CaseUtils.toCamelCase(tableName, true, '_') + "DTO";
        TypeSpec.Builder classDTOBuilder = TypeSpec.classBuilder(dtoName)
                .addModifiers(Modifier.PUBLIC);

        return classDTOBuilder.build();
    }
}
