package org.janus.generate.persistence.iface;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import org.apache.commons.text.CaseUtils;
import org.springframework.data.repository.CrudRepository;

import javax.lang.model.element.Modifier;
import java.util.List;

public class InterfaceRepository {

    public static TypeSpec generate(String tableName, List<String> listUKColumn, List<String> listSortableColumn) {

        String prefixName = CaseUtils.toCamelCase(tableName, true, '_');
        TypeVariableName entity = TypeVariableName.get(prefixName + "Entity");
        TypeVariableName idType = TypeVariableName.get("String").withBounds(String.class);

        TypeSpec.Builder builder = TypeSpec.interfaceBuilder(prefixName + "Repository")
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(CrudRepository.class), entity, idType))
                .addModifiers(Modifier.PUBLIC);
        // Method FindBy and Exists
        listUKColumn.forEach((item) -> {
            builder.addMethod(MethodSignatureFindBy.generate(entity, item))
                    .addMethod(MethodSignatureExists.generate(item));
        });

        // Method FindAllSortBy
        listSortableColumn.forEach((item) -> {
            builder.addMethod(MethodSignatureFindAllSortable.generate(entity, item));
        });
        return builder.build();
    }
}
