package org.janus.db;

import java.util.List;

class DBTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.Test
    void getTables() {
        List<String> listAutoloan = TableDB.getTables("autoloan");
        for (String s : listAutoloan) {
            System.out.println("Table");
            System.out.println(s);
            System.out.println("Columns");
            List<ColumnSimpleSpec> listColumn = ColumnDB.getSimpleColumns("autoloan", s);
            for (ColumnSimpleSpec x : listColumn) {
                System.out.println(x.name());
            }
            System.out.println("Many to One");
            List<ColumnManyToOneSpec> listMO = ColumnDB.getManyToOneColumns("autoloan", s);
            for (ColumnManyToOneSpec y : listMO) {
                System.out.println(y.name());
            }
            System.out.println("One to Many");
            List<ColumnOneToManySpec> listOM = ColumnDB.getOneToManyColumns("autoloan", s);
            for (ColumnOneToManySpec z : listOM) {
                System.out.println(z.tableName());
            }
            System.out.println("UK Columns");
            List<String> listUK = ColumnDB.getUKColumns("autoloan", s);
            for (String a : listUK) {
                System.out.println(a);
            }
        }
    }

}