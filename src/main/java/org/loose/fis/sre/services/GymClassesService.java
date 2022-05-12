package org.loose.fis.sre.services;

import java.sql.*;
import java.util.*;

import org.loose.fis.sre.exceptions.AppointmentAlreadyMade;
import org.loose.fis.sre.exceptions.ClassFull;

import org.loose.fis.sre.model.GymClass;
import org.loose.fis.sre.DatabaseConnection;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GymClassesService {

    private static Statement statement;
    private static PreparedStatement preparedStatement = null;
    private static DatabaseConnection connectNow = new DatabaseConnection();
    private static Connection connection = connectNow.getConnection();

    private static final String DATABASE_URL = "jdbc:mysql://localhost/gymapp";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "AnisiaRosu12";
    private static final String INSERT_QUERY = "INSERT INTO appointments (class_name, client_username) VALUES (?, ?)";

    static ResultSet queryOutput, resultSet;
    private static String connectQuery = "SELECT idappointments FROM appointments";

    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            queryOutput = statement.executeQuery(connectQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAppointment(String client_username, String class_name) throws ClassFull, SQLException, AppointmentAlreadyMade {
        GymClass gymclass = new GymClass(class_name);
        checkNumberOfParticipantsAndAppointmentsAlreadyMade(client_username, gymclass);
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        preparedStatement = connection.prepareStatement(INSERT_QUERY);
        preparedStatement.setString(1, class_name);
        preparedStatement.setString(2, client_username);
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }

    public static void checkNumberOfParticipantsAndAppointmentsAlreadyMade(String client_username, GymClass gymclass) throws ClassFull, SQLException, AppointmentAlreadyMade {
        String APPOINTMENT_QUERY = "SELECT * FROM appointments WHERE class_name = ? AND client_username = ?";
        preparedStatement = connection.prepareStatement(APPOINTMENT_QUERY);
        preparedStatement.setString(1, gymclass.getClass_name());
        preparedStatement.setString(2, client_username);
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
            throw new AppointmentAlreadyMade(gymclass.getClass_name());

        APPOINTMENT_QUERY = "SELECT * FROM appointments WHERE class_name = ?";
        preparedStatement = connection.prepareStatement(APPOINTMENT_QUERY);
        preparedStatement.setString(1, gymclass.getClass_name());
        resultSet = preparedStatement.executeQuery();
        int s = 0;
        while (resultSet.next()) {
            if(gymclass.getClass_name().equals(resultSet.getString("class_name")))
                s = s + 1;
        }
        if(s>=2)
            throw new ClassFull(gymclass.getClass_name());
    }
}