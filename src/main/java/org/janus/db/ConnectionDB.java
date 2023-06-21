package org.janus.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {

    public static Connection getConnection() {

        Connection newConn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            newConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "dev_user", "Gcc#081710");
        } catch (Exception e) {
            throw new ConnectionException("Error with database connection");
        }

        return newConn;
    }
}
