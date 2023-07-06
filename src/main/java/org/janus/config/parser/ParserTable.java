package org.janus.config.parser;

import org.janus.config.model.ConfigJanus;

import java.util.ArrayList;
import java.util.List;

public class ParserTable {

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
}
