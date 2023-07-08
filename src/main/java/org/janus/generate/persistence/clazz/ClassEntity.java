package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import jakarta.persistence.Entity;
import org.apache.commons.text.CaseUtils;
import org.janus.config.parser.TableSpec;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnOneToManySpec;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.persistence.annotation.AnnotationTable;
import org.janus.generate.persistence.field.FieldEntityManyToOne;
import org.janus.generate.persistence.field.FieldEntityOneToMany;
import org.janus.generate.persistence.field.FieldEntitySimple;
import org.janus.generate.persistence.method.*;

import javax.lang.model.element.Modifier;
import java.util.List;

public class ClassEntity {

    public static TypeSpec generate(TableSpec tableSpec, List<ColumnSimpleSpec> listColumn, List<ColumnManyToOneSpec> listColumnManyToOne, List<ColumnOneToManySpec> listColumnOneToMany, String rootPackage) {

        String entityName = CaseUtils.toCamelCase(tableSpec.name(), true, '_') + "Entity";
        TypeSpec.Builder classEntityBuilder = TypeSpec.classBuilder(entityName)
                .addAnnotation(Entity.class)
                .addAnnotation(AnnotationTable.generate(tableSpec.name()))
                .superclass(ClassName.get(rootPackage + ".helper.db", "MasterEntity"))
                .addModifiers(Modifier.PUBLIC);
        listColumn.forEach((item) -> {
            classEntityBuilder.addField(FieldEntitySimple.generate(item))
                    .addMethod(GetMethodSimple.generate(item))
                    .addMethod(SetMethodSimple.generate(item));
        });
        listColumnManyToOne.forEach((item) -> {
            classEntityBuilder.addField(FieldEntityManyToOne.generate(item, rootPackage + tableSpec.pack()))
                    .addMethod(GetEntityMethodManyToOne.generate(item, rootPackage + tableSpec.pack()))
                    .addMethod(SetEntityMethodManyToOne.generate(item, rootPackage + tableSpec.pack()));
        });
        listColumnOneToMany.forEach((item) -> {
            classEntityBuilder.addField(FieldEntityOneToMany.generate(item, rootPackage + tableSpec.pack()))
                    .addMethod(GetEntityMethodOneToMany.generate(item, rootPackage + tableSpec.pack()))
                    .addMethod(SetEntityMethodOneToMany.generate(item, rootPackage + tableSpec.pack()));
        });

        MethodSpec equals = MethodSpec.methodBuilder("equals")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(boolean.class)
                .addParameter(Object.class, "o")
                .addStatement("if (this == o) return true")
                .addStatement("if (o == null || getClass() != o.getClass()) return false")
                .addStatement("return new EqualsBuilder().appendSuper(super.equals(o)).isEquals()")
                .build();

        MethodSpec hashCode = MethodSpec.methodBuilder("hashCode")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(int.class)
                .addStatement("return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode()")
                .build();

        classEntityBuilder.addMethod(equals)
                .addMethod(hashCode);

        return classEntityBuilder.build();
    }
}
