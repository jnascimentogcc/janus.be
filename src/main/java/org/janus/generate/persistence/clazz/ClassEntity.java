package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import jakarta.persistence.Entity;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnOneToManySpec;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.persistence.annotation.AnnotationTable;
import org.janus.generate.persistence.field.FieldManyToOne;
import org.janus.generate.persistence.field.FieldOneToMany;
import org.janus.generate.persistence.field.FieldSimple;
import org.janus.generate.persistence.method.*;

import javax.lang.model.element.Modifier;
import java.util.List;

public class ClassEntity {

    public static TypeSpec generate(String tableName, List<ColumnSimpleSpec> listColumn, List<ColumnManyToOneSpec> listColumnManyToOne, List<ColumnOneToManySpec> listColumnOneToMany) {

        String entityName = CaseUtils.toCamelCase(tableName, true, '_') + "Entity";
        TypeSpec.Builder classEntityBuilder = TypeSpec.classBuilder(entityName)
                .addAnnotation(Entity.class)
                .addAnnotation(AnnotationTable.generate(tableName))
                .superclass(ClassName.get("com.kadipe.helper", "MasterEntity"))
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
