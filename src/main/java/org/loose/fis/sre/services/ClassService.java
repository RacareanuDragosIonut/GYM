package org.loose.fis.sre.services;
import java.util.*;
import java.sql.*;

import org.loose.fis.sre.exceptions.Classdoesnotexist;
import org.loose.fis.sre.exceptions.DayandHourTaken;
import org.loose.fis.sre.exceptions.Classwiththatscheduledoesnotexist;
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

    public static void addClass(String type, String day, String hour, int numberofclients) throws DayandHourTaken, SQLException {
        Class gymclass = new Class(type, day, hour, numberofclients);
        checkDayandHourTaken(gymclass);
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        preparedStatement = connection.prepareStatement(INSERT_QUERY);
        preparedStatement.setString(1, type);
        preparedStatement.setString(2, day);
        preparedStatement.setString(3, hour);
        preparedStatement.setInt(4, numberofclients);

        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }

    public static ArrayList<Class> getAllClasses() {
        try {
            ArrayList<Class> gymclasses = new ArrayList<>();
            statement.execute("SELECT * FROM classes");
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next())
                gymclasses.add(new Class(resultSet.getString("type"), resultSet.getString("day"),
                        resultSet.getString("hour"), resultSet.getInt("numberofclients")));

            return gymclasses.size() == 0 ? null : gymclasses;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void checkDayandHourTaken(Class gymclass) throws DayandHourTaken {
        ArrayList<Class> gymclasses = getAllClasses();

        if (gymclasses != null) {
            Iterator<Class> it = gymclasses.iterator();
            while (it.hasNext()) {
                Class aux = it.next();
                if (aux.gethour().equals(gymclass.gethour()) && aux.getday().equals(gymclass.getday()))
                    throw new DayandHourTaken(gymclass.getday(), gymclass.gethour());
            }
        }

    }
    public static void editClass(String type, String day, String hour, int numberofclients) throws SQLException, Classwiththatscheduledoesnotexist {
        Class gymclass = new Class(type, day, hour, numberofclients);
        checkClasswiththatscheduledoesnotexist(gymclass);
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        String edit = "UPDATE classes SET type = '" + type + "'WHERE day = '" + day + "'AND hour = '" + hour + "' ";
        preparedStatement = connection.prepareStatement(edit);

        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }

    private static void checkClasswiththatscheduledoesnotexist(Class gymclass) throws Classwiththatscheduledoesnotexist {
        ArrayList<Class> gymclasses = getAllClasses();
        int ok = 0;
        if (gymclasses == null)
            throw new Classwiththatscheduledoesnotexist(gymclass.getday(), gymclass.gethour());
        else {
            Iterator<Class> it = gymclasses.iterator();
            while (it.hasNext()) {
                Class aux = it.next();
                if (aux.gethour().equals(gymclass.gethour()) && (aux.getday()).equals(gymclass.getday())) {
                    ok = 1;
                    break;
                }
            }
            if (ok == 0)
                throw new Classwiththatscheduledoesnotexist(gymclass.getday(), gymclass.gethour());

        }

    }
}

