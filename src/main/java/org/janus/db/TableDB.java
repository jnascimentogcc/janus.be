package org.janus.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableDB {

    public static List<String> getTables(String schemaName) {

        List<String> listTable = new ArrayList<>();
        try {
            Connection connection = ConnectionDB.getConnection();
            Statement stmtTable = connection.createStatement();
            ResultSet rsTable = stmtTable.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '" + schemaName.toUpperCase() + "'");
            while (rsTable.next()) {
                listTable.add(rsTable.getString(1).toUpperCase());
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listTable;
    }
}
