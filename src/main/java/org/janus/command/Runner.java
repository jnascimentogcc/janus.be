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
import org.janus.generate.persistence.clazz.ClassEntity;
import org.janus.generate.persistence.clazz.MasterEntity;
import org.janus.generate.persistence.clazz.TableKeyHelper;
import org.janus.generate.persistence.iface.InterfaceRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Runner {

    public static void execute(ConfigJanus configJanus) {

        Path path = Paths.get("/projetos/noob/target");

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

        List<TableSpec> listTable = ParserTable.getTables(configJanus);
        listTable.forEach((item) -> {
            try {
                List<ColumnSimpleSpec> listColumnSimple = ColumnDB.getSimpleColumns(configJanus.getDatabaseSchema(), item.name());
                List<ColumnManyToOneSpec> listColumnManyToOne = ColumnDB.getManyToOneColumns(configJanus.getDatabaseSchema(), item.name());
                List<ColumnOneToManySpec> listColumnOneToMany = ColumnDB.getOneToManyColumns(configJanus.getDatabaseSchema(), item.name());
                JavaFile.Builder builder = JavaFile.builder(configJanus.getRootPackage() + item.pack() + ".repository",
                        ClassEntity.generate(item.name(), listColumnSimple, listColumnManyToOne, listColumnOneToMany));
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
