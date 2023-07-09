package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.config.parser.TableSpec;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnOneToManySpec;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.persistence.field.*;
import org.janus.generate.persistence.method.*;

import javax.lang.model.element.Modifier;
import java.util.List;

public class ClassDTO {

    public static TypeSpec generate(TableSpec tableSpec, List<ColumnSimpleSpec> listColumn, List<ColumnManyToOneSpec> listColumnManyToOne, List<ColumnOneToManySpec> listColumnOneToMany, String rootPackage) {

        String dtoName = CaseUtils.toCamelCase(tableSpec.name(), true, '_') + "DTO";
        TypeSpec.Builder classDTOBuilder = TypeSpec.classBuilder(dtoName)
                .superclass(ClassName.get(rootPackage + ".helper.model", "MasterDTO"))
                .addModifiers(Modifier.PUBLIC);
        listColumn.forEach((item) -> {
            classDTOBuilder.addField(FieldDTOSimple.generate(item))
                    .addMethod(GetMethodSimple.generate(item))
                    .addMethod(SetMethodSimple.generate(item));
        });
        listColumnManyToOne.forEach((item) -> {
            classDTOBuilder.addField(FieldDTOManyToOne.generate(item, rootPackage + tableSpec.pack()))
                    .addMethod(GetDTOMethodManyToOne.generate(item, rootPackage + tableSpec.pack()))
                    .addMethod(SetDTOMethodManyToOne.generate(item, rootPackage + tableSpec.pack()));
        });
        listColumnOneToMany.forEach((item) -> {
            classDTOBuilder.addField(FieldDTOOneToMany.generate(item, rootPackage + tableSpec.pack()))
                    .addMethod(GetDTOMethodOneToMany.generate(item, rootPackage + tableSpec.pack()))
                    .addMethod(SetDTOMethodOneToMany.generate(item, rootPackage + tableSpec.pack()));
        });

        return classDTOBuilder.build();
    }
}
