package org.janus.command;

import com.squareup.javapoet.JavaFile;
import jakarta.persistence.FetchType;
import org.janus.config.model.ConfigJanus;
import org.janus.config.parser.ParserTable;
import org.janus.config.parser.TableSpec;
import org.janus.db.ColumnDB;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnOneToManySpec;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.controller.ControllerExceptionHandler;
import org.janus.generate.exception.ItemNotFoundException;
import org.janus.generate.persistence.clazz.*;
import org.janus.generate.persistence.iface.InterfaceRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Runner {

    public static void execute(ConfigJanus configJanus) {

        Path path = Paths.get("/projetos/noob/target");

        try {
            JavaFile keyHelper = JavaFile.builder(configJanus.getRootPackage() + ".helper.exception", ItemNotFoundException.generate())
                    .build();
            keyHelper.writeTo(System.out);
            keyHelper.writeTo(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            JavaFile keyHelper = JavaFile.builder(configJanus.getRootPackage() + ".helper.controller", ControllerExceptionHandler.generate())
                    .build();
            keyHelper.writeTo(System.out);
            keyHelper.writeTo(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            JavaFile keyHelper = JavaFile.builder(configJanus.getRootPackage() + ".helper.db", TableKeyHelper.generate())
                    .build();
            keyHelper.writeTo(System.out);
            keyHelper.writeTo(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            JavaFile keyHelper = JavaFile.builder(configJanus.getRootPackage() + ".helper.db", MasterEntity.generate("id"))
                    .build();
            keyHelper.writeTo(System.out);
            keyHelper.writeTo(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            JavaFile keyHelper = JavaFile.builder(configJanus.getRootPackage() + ".helper.db", MasterDTO.generate("id"))
                    .build();
            keyHelper.writeTo(System.out);
            keyHelper.writeTo(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<TableSpec> listTable = ParserTable.getTables(configJanus);
        listTable.forEach((item) -> {
            try {
                List<ColumnSimpleSpec> listColumnSimple = item.columns();
                List<ColumnManyToOneSpec> listColumnManyToOne = item.manytoone();
                List<ColumnOneToManySpec> listColumnOneToMany = item.onetomany();
                JavaFile.Builder builderEntity = JavaFile.builder(configJanus.getRootPackage() + item.pack() + ".repository",
                        ClassEntity.generate(item.name(), listColumnSimple, listColumnManyToOne, listColumnOneToMany));
                if (listColumnManyToOne.size() > 0 || listColumnOneToMany.size() > 0) {
                    builderEntity.addStaticImport(FetchType.LAZY);
                }
                JavaFile classEntity = builderEntity
                        .build();
                classEntity.writeTo(System.out);
                classEntity.writeTo(path);

                JavaFile.Builder builderDTO = JavaFile.builder(configJanus.getRootPackage() + item.pack() + ".model",
                        ClassDTO.generate(item.name(), listColumnSimple, listColumnManyToOne, listColumnOneToMany));
                JavaFile classDTO = builderDTO
                        .build();
                classDTO.writeTo(System.out);
                classDTO.writeTo(path);

                // TODO: One Service
                JavaFile.Builder builderService = JavaFile.builder(configJanus.getRootPackage() + item.pack() + ".service",
                        ClassService.generate(item.name(), listColumnSimple, listColumnManyToOne, listColumnOneToMany));
                JavaFile classService = builderService
                        .build();
                classService.writeTo(System.out);
                classService.writeTo(path);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        listTable.forEach((item) -> {
            try {
                List<String> listUKColumn = ColumnDB.getUKColumns(configJanus.getDatabaseSchema(), item.name());
                JavaFile interfaceRepository = JavaFile
                        .builder(configJanus.getRootPackage() +  item.pack() + ".repository", InterfaceRepository.generate(item.name(), listUKColumn))
                        .build();
                interfaceRepository.writeTo(System.out);
                interfaceRepository.writeTo(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }
}
