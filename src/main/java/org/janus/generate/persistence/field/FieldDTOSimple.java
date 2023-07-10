package org.janus.generate.persistence.field;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.GenUtil;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;

public class FieldDTOSimple {

    public static FieldSpec generate(ColumnSimpleSpec columnSimpleSpec) {

        Type typeField = GenUtil.getTypeField(columnSimpleSpec);

        FieldSpec.Builder builder = FieldSpec.builder(typeField, CaseUtils.toCamelCase(columnSimpleSpec.name(), false, '_'))
                .addModifiers(Modifier.PRIVATE);
        if (!columnSimpleSpec.nullable()) {
                    builder.addAnnotation(NotBlank.class);
            if ("CHAR".equals(columnSimpleSpec.type()) || "VARCHAR".equals(columnSimpleSpec.type())) {
                builder.addAnnotation(AnnotationSpec.builder(Size.class)
                        .addMember("min", "$L", 1)
                        .addMember("max", "$L", columnSimpleSpec.size())
                        .build());
            }
        }

        return builder.build();
    }

}
