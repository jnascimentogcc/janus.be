package org.janus.generate;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import jakarta.persistence.Entity;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnSpec;

import javax.lang.model.element.Modifier;
import java.util.List;

public class ClassEntity {

    public static TypeSpec generate(String tableName, List<ColumnSpec> listColumn) {

        TypeSpec.Builder classEntityBuilder = TypeSpec.classBuilder(CaseUtils.toCamelCase(tableName, true, '_') + "Entity")
                .addAnnotation(Entity.class)
                .addAnnotation(AnnotationTable.generate(tableName))
                .superclass(ClassName.get("com.kadipe.demo.helper", "MasterEntity"))
                .addModifiers(Modifier.PUBLIC);
        listColumn.forEach((item) -> {
            if (!item.pk()) {
                classEntityBuilder.addField(FieldSimple.generate(item))
                .addMethod(GetMethodSimple.generate(item))
                .addMethod(SetMethodSimple.generate(item));
            }
        });

        return classEntityBuilder.build();
    }
}
