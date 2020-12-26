package ru.vsu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SimpleConnectionBuilder implements ConnectionBuilder {
    @Override
    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/test_db";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","admin");
        Connection connection = DriverManager.getConnection(url, props);
        return connection;
    }
}
