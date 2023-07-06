package org.janus.generate.persistence.field;

import com.squareup.javapoet.FieldSpec;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.GenUtil;
import org.janus.generate.persistence.annotation.AnnotationColumn;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FieldEntitySimple {

    public static FieldSpec generate(ColumnSimpleSpec columnSimpleSpec) {

        Type typeField = GenUtil.getTypeField(columnSimpleSpec);

        return FieldSpec.builder(typeField, CaseUtils.toCamelCase(columnSimpleSpec.name(), false, '_'))
                .addAnnotation(AnnotationColumn.generate(columnSimpleSpec))
                .addModifiers(Modifier.PRIVATE)
                .build();
    }
}
