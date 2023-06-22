package org.janus.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ColumnDB {

    public static List<ColumnSimpleSpec> getSimpleColumns(String schemaName, String tableName) {

        List<ColumnSimpleSpec> listColumn = new ArrayList<>();
        try {
            Connection connection = ConnectionDB.getConnection();
            Statement stmtColumn = connection.createStatement();
            ResultSet rsColumn = stmtColumn.executeQuery("SELECT COLUMN_NAME, " +
                    "DATA_TYPE, " +
                    "CHARACTER_MAXIMUM_LENGTH, " +
                    "IS_NULLABLE, " +
                    "COLUMN_KEY " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_SCHEMA = '" + schemaName + "' " +
                    "AND TABLE_NAME = '" + tableName + "' ORDER BY ORDINAL_POSITION");
            while (rsColumn.next()) {
                if (!"PRI" .equals(rsColumn.getString(5)) && !"MUL" .equals(rsColumn.getString(5))) {
                    ColumnSimpleSpec columnSimpleSpec = new ColumnSimpleSpec(rsColumn.getString(1),
                            rsColumn.getString(2),
                            rsColumn.getInt(3),
                            "YES" .equals(rsColumn.getString(4)));
                    listColumn.add(columnSimpleSpec);
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listColumn;
    }

    public static List<ColumnManyToOneSpec> getManyToOneColumns(String schemaName, String tableName) {

        List<ColumnManyToOneSpec> listColumn = new ArrayList<>();
        try {
            Connection connection = ConnectionDB.getConnection();
            Statement stmtColumn = connection.createStatement();
            ResultSet rsColumn = stmtColumn.executeQuery("SELECT COLUMN_NAME, " +
                    "IS_NULLABLE, " +
                    "COLUMN_KEY " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_SCHEMA = '" + schemaName + "' " +
                    "AND TABLE_NAME = '" + tableName + "' ORDER BY ORDINAL_POSITION");
            while (rsColumn.next()) {
                if ("MUL" .equals(rsColumn.getString(3))) {
                    Statement stmtColumnFK = connection.createStatement();
                    ResultSet rsColumnFK = stmtColumnFK.executeQuery("SELECT REFERENCED_TABLE_NAME " +
                            "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                            "WHERE TABLE_SCHEMA = '" + schemaName + "' " +
                            "AND TABLE_NAME = '" + tableName + "' AND " +
                            "COLUMN_NAME = '" + rsColumn.getString(1) + "'");
                    while (rsColumnFK.next()) {
                        ColumnManyToOneSpec columnManyToOneSpec = new ColumnManyToOneSpec(
                                rsColumn.getString(1), rsColumnFK.getString(1), "YES" .equals(rsColumn.getString(2)));
                        listColumn.add(columnManyToOneSpec);
                    }
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listColumn;
    }

    public static List<ColumnOneToManySpec> getOneToManyColumns(String schemaName, String tableName) {

        List<ColumnOneToManySpec> listColumn = new ArrayList<>();
        try {
            Connection connection = ConnectionDB.getConnection();
            Statement stmtColumn = connection.createStatement();
            ResultSet rsColumn = stmtColumn.executeQuery("SELECT TABLE_NAME, " +
                    "REFERENCED_TABLE_NAME " +
                    "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                    "WHERE TABLE_SCHEMA = '" + schemaName + "' " +
                    "AND REFERENCED_TABLE_NAME = '" + tableName + "' ORDER BY ORDINAL_POSITION");
            while (rsColumn.next()) {
                ColumnOneToManySpec columnOneToManySpec = new ColumnOneToManySpec(
                        rsColumn.getString(1), rsColumn.getString(2));
                listColumn.add(columnOneToManySpec);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listColumn;
    }

    public static List<String> getUKColumns(String schemaName, String tableName) {

        List<String> listColumn = new ArrayList<>();
        try {
            Connection connection = ConnectionDB.getConnection();
            Statement stmtColumn = connection.createStatement();
            ResultSet rsColumn = stmtColumn.executeQuery("SELECT COLUMN_NAME " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_SCHEMA = '" + schemaName + "' " +
                    "AND TABLE_NAME = '" + tableName + "' " +
                    "AND COLUMN_KEY = 'UNI'");
            while (rsColumn.next()) {
                listColumn.add(rsColumn.getString(1));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listColumn;
    }

}
