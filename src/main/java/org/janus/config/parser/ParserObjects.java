package org.janus.config.parser;

import org.janus.config.model.BuzzProcess;
import org.janus.config.model.ConfigJanus;
import org.janus.db.ColumnSimpleSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParserObjects {

    static String pack = "";

    public static List<TableSpec> getTables(ConfigJanus configJanus) {

        List<TableSpec> listTable = new ArrayList<>();
        configJanus.getBuzzUnits().forEach((itemUnit) -> {
            pack = "." + itemUnit.getPackageName();
            itemUnit.getBuzzAreas().forEach((itemArea) -> {
                pack = pack + "." + itemArea.getPackageName();
                itemArea.getBuzzProcesses().forEach((itemProcess) -> {
                    pack = pack + "." + itemProcess.getPackageName();
                    itemProcess.getCruds().forEach((itemCrud) -> {
                        listTable.add(new TableSpec(itemCrud.getTable(), pack, itemCrud.getColumns(), itemCrud.getOnetomany(), itemCrud.getManytoone()));
                    });
                });
            });
        });

        return listTable;
    }

    public static List<BuzzProcess> getServices(ConfigJanus configJanus) {

        List<BuzzProcess> listServices = new ArrayList<>();
        configJanus.getBuzzUnits().forEach((itemUnit) -> {
            pack = "." + itemUnit.getPackageName();
            itemUnit.getBuzzAreas().forEach((itemArea) -> {
                pack = pack + "." + itemArea.getPackageName();
                itemArea.getBuzzProcesses().forEach((itemProcess) -> {
                    itemProcess.setPackageName(pack + "." + itemProcess.getPackageName());
                    listServices.add(itemProcess);
                });
            });
        });

        return listServices;
    }

    public static List<String> getUKColumns(TableSpec tableSpec) {

        return tableSpec.columns().stream()
                .filter(ColumnSimpleSpec::unique)
                .map(ColumnSimpleSpec::name)
                .collect(Collectors.toList());
    }

    public static List<String> getSortableColumns(TableSpec tableSpec) {

        return tableSpec.columns().stream()
                .filter(ColumnSimpleSpec::sortable)
                .map(ColumnSimpleSpec::name)
                .collect(Collectors.toList());
    }
}
