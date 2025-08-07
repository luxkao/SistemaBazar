package com.luxkao.bazar.model.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String URL = "jdbc:mysql://localhost:3306/bazar_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static Connection currentConnection = null;

    public static Connection getCurrentConnection() throws SQLException {

        if (currentConnection == null || currentConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                currentConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return currentConnection;
    }
}