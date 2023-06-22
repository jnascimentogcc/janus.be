package org.janus.generate.clazz;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import jakarta.persistence.Entity;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnOneToManySpec;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.field.FieldOneToMany;
import org.janus.generate.method.*;
import org.janus.generate.annotation.AnnotationTable;
import org.janus.generate.field.FieldManyToOne;
import org.janus.generate.field.FieldSimple;

import javax.lang.model.element.Modifier;
import java.util.List;

public class ClassEntity {

    public static TypeSpec generate(String tableName, List<ColumnSimpleSpec> listColumn, List<ColumnManyToOneSpec> listColumnManyToOne, List<ColumnOneToManySpec> listColumnOneToMany) {

        TypeSpec.Builder classEntityBuilder = TypeSpec.classBuilder(CaseUtils.toCamelCase(tableName, true, '_') + "Entity")
                .addAnnotation(Entity.class)
                .addAnnotation(AnnotationTable.generate(tableName))
                .superclass(ClassName.get("com.kadipe.demo.helper", "MasterEntity"))
                .addModifiers(Modifier.PUBLIC);
        listColumn.forEach((item) -> {
            classEntityBuilder.addField(FieldSimple.generate(item))
                    .addMethod(GetMethodSimple.generate(item))
                    .addMethod(SetMethodSimple.generate(item));
        });
        listColumnManyToOne.forEach((item) -> {
            classEntityBuilder.addField(FieldManyToOne.generate(item))
                    .addMethod(GetMethodManyToOne.generate(item))
                    .addMethod(SetMethodManyToOne.generate(item));
        });
        listColumnOneToMany.forEach((item) -> {
            classEntityBuilder.addField(FieldOneToMany.generate(item))
                    .addMethod(GetMethodOneToMany.generate(item))
                    .addMethod(SetMethodOneTOMany.generate(item));
        });

        return classEntityBuilder.build();
    }
}
