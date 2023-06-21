package org.janus;

import com.squareup.javapoet.JavaFile;
import org.janus.db.ColumnDB;
import org.janus.db.ColumnSpec;
import org.janus.db.TableDB;
import org.janus.generate.ClassEntity;
import org.janus.generate.InterfaceRepository;
import org.janus.generate.MasterEntity;
import org.janus.generate.TableKeyHelper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        Path path = Paths.get("/projetos/noob/target");

        try {
            JavaFile keyHelper = JavaFile.builder("com.kadipe.demo.helper", TableKeyHelper.generate())
                    .build();
            keyHelper.writeTo(System.out);
            keyHelper.writeTo(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            JavaFile keyHelper = JavaFile.builder("com.kadipe.demo.helper", MasterEntity.generate("id"))
                    .build();
            keyHelper.writeTo(System.out);
            keyHelper.writeTo(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> listTable = TableDB.getTables("DEMO");
        listTable.forEach((item) -> {
            try {
                List<ColumnSpec> listColumn = ColumnDB.getColumns("DEMO", item);
                JavaFile classEntity = JavaFile.builder("com.kadipe.demo.user.repository", ClassEntity.generate(item, listColumn))
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
