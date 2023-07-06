package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnOneToManySpec;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.persistence.field.*;
import org.janus.generate.persistence.method.*;

import javax.lang.model.element.Modifier;
import java.util.List;

public class ClassDTO {

    public static TypeSpec generate(String tableName, List<ColumnSimpleSpec> listColumn, List<ColumnManyToOneSpec> listColumnManyToOne, List<ColumnOneToManySpec> listColumnOneToMany) {

        String dtoName = CaseUtils.toCamelCase(tableName, true, '_') + "DTO";
        TypeSpec.Builder classDTOBuilder = TypeSpec.classBuilder(dtoName)
                .superclass(ClassName.get("com.kadipe.helper", "MasterDTO"))
                .addModifiers(Modifier.PUBLIC);
        listColumn.forEach((item) -> {
            classDTOBuilder.addField(FieldDTOSimple.generate(item))
                    .addMethod(GetMethodSimple.generate(item))
                    .addMethod(SetMethodSimple.generate(item));
        });
        listColumnManyToOne.forEach((item) -> {
            classDTOBuilder.addField(FieldDTOManyToOne.generate(item))
                    .addMethod(GetDTOMethodManyToOne.generate(item))
                    .addMethod(SetDTOMethodManyToOne.generate(item));
        });
        listColumnOneToMany.forEach((item) -> {
            classDTOBuilder.addField(FieldDTOOneToMany.generate(item))
                    .addMethod(GetDTOMethodOneToMany.generate(item))
                    .addMethod(SetDTOMethodOneToMany.generate(item));
        });

        return classDTOBuilder.build();
    }
}
