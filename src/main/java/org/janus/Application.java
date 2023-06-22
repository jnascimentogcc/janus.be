package org.janus;

import com.squareup.javapoet.JavaFile;
import jakarta.persistence.FetchType;
import org.janus.db.*;
import org.janus.generate.clazz.ClassEntity;
import org.janus.generate.iface.InterfaceRepository;
import org.janus.generate.clazz.MasterEntity;
import org.janus.generate.clazz.TableKeyHelper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        Path path = Paths.get("/projetos/noob/target");

        try {
            JavaFile keyHelper = JavaFile.builder("com.kadipe.helper", TableKeyHelper.generate())
                    .build();
            keyHelper.writeTo(System.out);
            keyHelper.writeTo(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            JavaFile keyHelper = JavaFile.builder("com.kadipe.helper", MasterEntity.generate("id"))
                    .build();
            keyHelper.writeTo(System.out);
            keyHelper.writeTo(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> listTable = TableDB.getTables("KADIPE");
        listTable.forEach((item) -> {
            try {
                List<ColumnSimpleSpec> listColumnSimple = ColumnDB.getSimpleColumns("KADIPE", item);
                List<ColumnManyToOneSpec> listColumnManyToOne = ColumnDB.getManyToOneColumns("KADIPE", item);
                List<ColumnOneToManySpec> listColumnOneToMany = ColumnDB.getOneToManyColumns("KADIPE", item);
                JavaFile.Builder builder = JavaFile.builder("com.kadipe.demo.user.repository",
                        ClassEntity.generate(item, listColumnSimple, listColumnManyToOne, listColumnOneToMany));
                if (listColumnManyToOne.size() > 0 || listColumnOneToMany.size() > 0) {
                    builder.addStaticImport(FetchType.LAZY);
                }
                JavaFile classEntity = builder
                        .build();
                classEntity.writeTo(System.out);
                classEntity.writeTo(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        listTable.forEach((item) -> {
            try {
                JavaFile interfaceRepository = JavaFile.builder("com.kadipe.demo.user.repository", InterfaceRepository.generate(item))
                        .build();
                interfaceRepository.writeTo(System.out);
                interfaceRepository.writeTo(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
