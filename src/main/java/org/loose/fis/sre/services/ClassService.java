package org.loose.fis.sre.services;
import java.util.*;
import java.sql.*;

import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.model.Class;
import org.loose.fis.sre.DatabaseConnection;
import org.loose.fis.sre.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ClassService {
    private static Statement statement;
    private static PreparedStatement preparedStatement = null;
    private static DatabaseConnection connectNow = new DatabaseConnection();
    private static Connection connection = connectNow.getConnection();
    private static final String DATABASE_URL = "jdbc:mysql://localhost/gymapp";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "douazecisiunu2121";
    private static final String INSERT_QUERY = "INSERT INTO classes (type, day, hour,numberofclients) VALUES (?, ?, ?, ?)";
    private static String connectQuery = "SELECT type,day,hour,numberofclients FROM classes";

    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static ResultSet queryOutput, resultSet;
    static {
        try {
            queryOutput = statement.executeQuery(connectQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
