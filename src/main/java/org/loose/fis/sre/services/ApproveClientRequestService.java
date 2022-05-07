package org.loose.fis.sre.services;

import java.sql.*;
import java.util.*;

import org.loose.fis.sre.exceptions.Nospacesavailable;
import org.loose.fis.sre.model.Client;
import org.loose.fis.sre.DatabaseConnection;



public class ApproveClientRequestService {
    private static Statement statement;
    private static PreparedStatement preparedStatement = null;
    private static DatabaseConnection connectNow = new DatabaseConnection();
    private static Connection connection = connectNow.getConnection();

    private static final String DATABASE_URL = "jdbc:mysql://localhost/gymapp";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "douazecisiunu2121";
    private static final String INSERT_QUERY = "INSERT INTO clients(client_information) VALUES (?)";
    static ResultSet queryOutput, resultSet;
    private static String connectQuery = "SELECT * FROM clients";

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
    public static void approveclient(String client_information) throws Nospacesavailable, SQLException {
        Client client = new Client(client_information);
        checkNumberOfParticipants(client);
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        preparedStatement = connection.prepareStatement(INSERT_QUERY);
        preparedStatement.setString(1, client_information);
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }



    public static void checkNumberOfParticipants(Client client) throws Nospacesavailable, SQLException {
        preparedStatement = connection.prepareStatement(connectQuery);

        resultSet = preparedStatement.executeQuery();
        int s = 0;
        while (resultSet.next())
                s = s + 1;

        if(s>=10)
            throw new Nospacesavailable();
    }
}

