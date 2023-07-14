package org.janus.command;

import com.squareup.javapoet.JavaFile;
import jakarta.persistence.FetchType;
import org.janus.config.model.BuzzProcess;
import org.janus.config.model.ConfigJanus;
import org.janus.config.parser.ParserObjects;
import org.janus.config.parser.TableSpec;
import org.janus.db.ColumnDB;
import org.janus.db.ColumnManyToOneSpec;
import org.janus.db.ColumnOneToManySpec;
import org.janus.db.ColumnSimpleSpec;
import org.janus.generate.controller.ControllerExceptionHandler;
import org.janus.generate.exception.ItemNotFoundException;
import org.janus.generate.persistence.clazz.*;
import org.janus.generate.persistence.iface.InterfaceRepository;
import org.janus.generate.props.PropertyGenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Runner {

    public static void execute(ConfigJanus configJanus) {

        Path pathJava = Paths.get("/projetos/noob/target/java");

        // Property (application.property)
        PropertyGenerator.generateProperty("/projetos/noob/target/resources", configJanus);

        try {
            // Exceptions
            JavaFile infException = JavaFile.builder(configJanus.getRootPackage() + ".helper.exception", ItemNotFoundException.generate())
                    .build();
            infException.writeTo(pathJava);

            // Helper Controller
            JavaFile ceHandler = JavaFile.builder(configJanus.getRootPackage() + ".helper.controller", ControllerExceptionHandler.generate())
                    .build();
            ceHandler.writeTo(pathJava);

            // Helper DB Key Generator
            JavaFile keyHelper = JavaFile.builder(configJanus.getRootPackage() + ".helper.db", TableKeyHelper.generate())
                    .build();
            keyHelper.writeTo(pathJava);

            // Helper DB Master Entity
            JavaFile masterEntity = JavaFile.builder(configJanus.getRootPackage() + ".helper.db",
                            MasterEntity.generate("id", configJanus.getRootPackage()))
                    .build();
            masterEntity.writeTo(pathJava);

            // Helper Model Master DTO
            JavaFile masterDTO = JavaFile.builder(configJanus.getRootPackage() + ".helper.model", MasterDTO.generate("id"))
                    .build();
            masterDTO.writeTo(pathJava);

            // Clazz Main Application
            JavaFile applicationMain = JavaFile.builder(configJanus.getRootPackage() , ClassApplication.generate(configJanus.getRootPackage()))
                    .build();
            applicationMain.writeTo(pathJava);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Entities and DTOs
        List<TableSpec> listTable = ParserObjects.getTables(configJanus);
        listTable.forEach((item) -> {
            try {
                List<ColumnSimpleSpec> listColumnSimple = item.columns();
                List<ColumnManyToOneSpec> listColumnManyToOne = item.manytoone();
                List<ColumnOneToManySpec> listColumnOneToMany = item.onetomany();

                // Entities
                JavaFile.Builder builderEntity = JavaFile.builder(configJanus.getRootPackage() + item.pack() + ".repository",
                        ClassEntity.generate(item, listColumnSimple, listColumnManyToOne, listColumnOneToMany, configJanus.getRootPackage()));
                if (listColumnManyToOne.size() > 0 || listColumnOneToMany.size() > 0) {
                    builderEntity.addStaticImport(FetchType.LAZY);
                }
                JavaFile classEntity = builderEntity
                        .build();
                classEntity.writeTo(pathJava);

                // DTOs
                JavaFile.Builder builderDTO = JavaFile.builder(configJanus.getRootPackage() + item.pack() + ".model",
                        ClassDTO.generate(item, listColumnSimple, listColumnManyToOne, listColumnOneToMany, configJanus.getRootPackage()));
                JavaFile classDTO = builderDTO
                        .build();
                classDTO.writeTo(pathJava);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Services
        List<BuzzProcess> listServices = ParserObjects.getServices(configJanus);
        listServices.forEach((item) -> {
            try {
                JavaFile.Builder builderService = JavaFile.builder(configJanus.getRootPackage() + item.getPackageName() + ".service",
                        ClassService.generate(item, configJanus.getRootPackage()));
                JavaFile classService = builderService
                        .build();
                classService.writeTo(pathJava);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Controller
        listServices.forEach((item) -> {
            try {
                JavaFile.Builder builderService = JavaFile.builder(configJanus.getRootPackage() + item.getPackageName() + ".controller",
                        ClassController.generate(item, configJanus.getRootPackage()));
                JavaFile classService = builderService
                        .build();
                classService.writeTo(pathJava);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Interface Repository
        listTable.forEach((item) -> {
            try {
//                List<String> listUKColumn = ColumnDB.getUKColumns(configJanus.getDatabaseSchema(), item.name());
                List<String> listUKColumn = ParserObjects.getUKColumns(item);
                List<String> listSortableColumn = ParserObjects.getSortableColumns(item);
                JavaFile interfaceRepository = JavaFile
                        .builder(configJanus.getRootPackage() +  item.pack() + ".repository", InterfaceRepository.generate(item.name(), listUKColumn, listSortableColumn))
                        .build();
                interfaceRepository.writeTo(pathJava);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
