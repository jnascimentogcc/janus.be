package org.janus.generate.persistence.clazz;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

import javax.lang.model.element.Modifier;

public class MasterEntity {

    public static TypeSpec generate(String idName, String rootPackage) {

        FieldSpec keyField = FieldSpec.builder(String.class, "id")
                .addAnnotation(Id.class)
                .addAnnotation(AnnotationSpec.builder(Column.class)
                        .addMember("name", "$S", idName.toUpperCase())
                        .addMember("length", "$L", 36)
                        .addMember("nullable", "$L", false)
                        .build())
                .addAnnotation(AnnotationSpec.builder(GenericGenerator.class)
                        .addMember("name", "$S", "sequenceUUID")
                        .addMember("strategy", "$S", rootPackage + ".helper.db.KeyGenerator")
                        .build())
                .addAnnotation(AnnotationSpec.builder(GeneratedValue.class)
                        .addMember("generator", "$S", "sequenceUUID")
                        .build())
                .addModifiers(Modifier.PRIVATE)
                .build();

        MethodSpec setKey = MethodSpec.methodBuilder("setId")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, idName)
                .addStatement("this.id = id")
                .build();

        MethodSpec getKey = MethodSpec.methodBuilder("getId")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return this.id")
                .build();

        MethodSpec equals = MethodSpec.methodBuilder("equals")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(boolean.class)
                .addParameter(Object.class, "o")
                .addStatement("if (this == o) return true")
                .addStatement("if (o == null || getClass() != o.getClass()) return false")
                .addStatement("MasterEntity that = (MasterEntity) o")
                .addStatement("return new EqualsBuilder().append(getId(), that.getId()).isEquals()")
                .build();

        MethodSpec hashCode = MethodSpec.methodBuilder("hashCode")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(int.class)
                .addStatement("return new HashCodeBuilder(17, 37).append(getId()).toHashCode()")
                .build();

        return TypeSpec.classBuilder("MasterEntity")
                .addAnnotation(MappedSuperclass.class)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addField(keyField)
                .addMethod(setKey)
                .addMethod(getKey)
                .addMethod(equals)
                .addMethod(hashCode)
                .build();
    }

}
