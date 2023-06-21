package org.janus.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ColumnDB {

    public static List<ColumnSpec> getColumns(String schemaName, String tableName) {

        List<ColumnSpec> listColumn = new ArrayList<>();
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
                ColumnSpec columnSpec = new ColumnSpec(rsColumn.getString(1),
                        rsColumn.getString(2),
                        rsColumn.getInt(3),
                        "YES".equals(rsColumn.getString(4)),
                        "PRI".equals(rsColumn.getString(5)));
                listColumn.add(columnSpec);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listColumn;
    }
}
