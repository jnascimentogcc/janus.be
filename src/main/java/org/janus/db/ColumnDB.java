package org.janus.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ColumnDB {

    public static List<String> getColumns(String schemaName, String tableName) {

        List<String> listColumn = new ArrayList<>();
        try {
            Connection connection = ConnectionDB.getConnection();
            Statement stmtColumn = connection.createStatement();
            ResultSet rsColumn = stmtColumn.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '" + schemaName + "' AND TABLE_NAME = '" + tableName + "' ORDER BY ORDINAL_POSITION");
            while (rsColumn.next()) {
                listColumn.add(rsColumn.getString(1).toUpperCase());
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listColumn;
    }
}
