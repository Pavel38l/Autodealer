package ru.vsu;


import ru.vsu.data.entity.Sex;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}
