package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.TypeSpec;
import jakarta.persistence.Entity;
import jakarta.transaction.Transactional;
import org.apache.commons.text.CaseUtils;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnOneToManySpec;
import org.janus.db.ColumnSimpleSpec;
import org.springframework.stereotype.Service;

import javax.lang.model.element.Modifier;
import java.util.List;

public class ClassService {

    public static TypeSpec generate(String serviceName) {

        String dtoName = CaseUtils.toCamelCase(serviceName, true, ' ') + "Service";
        TypeSpec.Builder classDTOBuilder = TypeSpec.classBuilder(dtoName)
                .addAnnotation(Service.class)
                .addAnnotation(Transactional.class)
                .addModifiers(Modifier.PUBLIC);

        return classDTOBuilder.build();
    }
}
